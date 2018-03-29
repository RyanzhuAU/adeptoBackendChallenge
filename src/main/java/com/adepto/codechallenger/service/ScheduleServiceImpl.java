package com.adepto.codechallenger.service;

import com.adepto.codechallenger.Constants;
import com.adepto.codechallenger.domain.Shift;
import com.adepto.codechallenger.domain.ShiftSchedule;
import com.adepto.codechallenger.domain.Staff;
import com.adepto.codechallenger.repository.ScheduleRepositoryImpl;
import com.adepto.codechallenger.repository.ShiftRepository;
import com.adepto.codechallenger.repository.ShiftScheduleRepository;
import com.adepto.codechallenger.repository.StaffRepository;
import com.adepto.codechallenger.representation.StaffRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    @Autowired
    private ScheduleRepositoryImpl scheduleRepository;

    public ScheduleServiceImpl(ShiftRepository shiftRepository, StaffRepository staffRepository, ShiftScheduleRepository shiftScheduleRepository) {
        this.shiftRepository = shiftRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Truncate table and load shift and staff data from selected csv file
     *
     * @param shiftFileName
     * @param staffFileName
     */

    public void loadScheduleData(String shiftFileName, String shiftTableName,
                                 String staffFileName, String staffTableName) throws Exception {
        try {
            logger.info("Truncate the shift and staff table");

            scheduleRepository.truncateTable(shiftTableName);
            scheduleRepository.truncateTable(staffTableName);

            logger.info("Load shift and staff data from selected csv files - shift file: " + shiftFileName + " staff file: " + staffFileName);
            scheduleRepository.loadDataFromCsv(shiftFileName, shiftTableName);
            scheduleRepository.loadDataFromCsv(staffFileName, staffTableName);
        }
        catch (DataAccessException de) {
            throw new Exception(Constants.ERROR_LOAD_CSV_FILE);
        }
    }

    /**
     *  Generate shift schedule based on the imported shift/staff data.
     */
    public void generateShiftSchedule() {
        //Separate the load shift/staff data and generate schedule actions.
//        this.loadScheduleData(Constants.SHIFT_FILE_NAME, Constants.STAFF_FILE_NAME);
        scheduleRepository.truncateTable(Constants.SCHEDULE_TABLE_NAME);

        List<Shift> shiftList = this.shiftRepository.findAll();
        List<Staff> staffList = this.staffRepository.findAll();

        // Allocate the shift to staff
        // Try to allocate shift equally to each staff
        Map<Integer, Staff> staffMap = new HashMap<>();
        List<StaffRep> staffScheduleList = new ArrayList<>();
        List<Shift> unallocatedShift = new ArrayList<>();

        for (Shift shift : shiftList) {

            //TODO if we need to have the function to focus on the unallocated shift only, we need to remove this code
            shift.setAllocateFlag(false);

            for (Staff staff : staffList) {
                if (staff.getValidRole().contains(shift.getRequiredRole()) && !staff.getDaysUnavailable().contains(shift.getDay()) &&
                        staff.getMaxHours() >= shift.getHours() && staffMap.get(staff.getId()) == null) {
                    ShiftSchedule shiftSchedule = new ShiftSchedule(shift, staff);
                    shiftScheduleRepository.save(shiftSchedule);

//                    scheduleRepository.saveShiftSchedule(shift.getShiftId(), staff.getStaffId());

                    staffMap.put(staff.getId(), staff);

                    Integer availableHours = staff.getMaxHours() - shift.getHours();
                    StaffRep staffRep = new StaffRep(staff, availableHours);
                    staffScheduleList.add(staffRep);

                    shift.setAllocateFlag(true);

                    break;
                }
            }

            sortStaffShiftListByAvailableHour(staffScheduleList);

            if (!shift.getAllocateFlag()) {
                for (StaffRep staffRep : staffScheduleList) {
                    Staff s = staffRep.getStaff();
                    if (s.getValidRole().contains(shift.getRequiredRole()) && !s.getDaysUnavailable().contains(shift.getDay())
                            && staffRep.getAvailableHours() >= shift.getHours()) {
                        ShiftSchedule shiftSchedule = new ShiftSchedule(shift, s);
                        shiftScheduleRepository.save(shiftSchedule);

                        staffRep.updateAvailableHours(shift.getHours());

                        shift.setAllocateFlag(true);

                        break;
                    }
                }
            }

            if (!shift.getAllocateFlag()) {
                unallocatedShift.add(shift);
            }

            shiftRepository.save(shift);
        }

        // TODO need to do more analysis for unallocated shifts

    }

    private void sortStaffShiftListByAvailableHour(List<StaffRep> staffReps) {
        Collections.sort(staffReps, (o1, o2) -> {
            if (o1.getAvailableHours() == o2.getAvailableHours())
                return 0;
            else if (o1.getAvailableHours() > o2.getAvailableHours()){
                return 1;
            }
            else {
                return -1;
            }
        });
    }
}
