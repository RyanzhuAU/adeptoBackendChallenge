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

    public ScheduleServiceImpl(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Truncate table and load shift and staff data from selected csv file
     *
     * @param shiftFileName
     * @param staffFileName
     */

    public void loadScheduleData(String shiftFileName, String staffFileName) {
        logger.info("Truncate the shift and staff table");

        scheduleRepository.truncateTable(Constants.SHIFT_TABLE_NAME);
        scheduleRepository.truncateTable(Constants.STAFF_TABLE_NAME);

        logger.info("Load shift and staff data from selected csv files - shift file: " + shiftFileName + " staff file: " + staffFileName);
        scheduleRepository.loadDataFromCsv(shiftFileName, Constants.SHIFT_TABLE_NAME);
        scheduleRepository.loadDataFromCsv(staffFileName, Constants.STAFF_TABLE_NAME);
    }

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
                        staff.getMaxHours() >= shift.getHours() && staffMap.get(staff.getStaffId()) == null) {
                    scheduleRepository.saveShiftSchedule(shift.getShiftId(), staff.getStaffId());

                    staffMap.put(staff.getStaffId(), staff);

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
                        scheduleRepository.saveShiftSchedule(shift.getShiftId(), s.getStaffId());

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
