package com.adepto.codechallenger.service;

import com.adepto.codechallenger.Constants;
import com.adepto.codechallenger.domain.Shift;
import com.adepto.codechallenger.domain.ShiftSchedule;
import com.adepto.codechallenger.domain.Staff;
import com.adepto.codechallenger.repository.ShiftRepository;
import com.adepto.codechallenger.repository.ShiftScheduleRepository;
import com.adepto.codechallenger.repository.StaffRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {
    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void testLoadScheduleData() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File shiftFile = new File(classLoader.getResource("shifts.csv").getFile());
        File staffFile = new File(classLoader.getResource("staff.csv").getFile());

        List<Shift> shiftList;
        List<Staff> staffList;

        if (shiftFile.exists() && staffFile.exists()) {
            scheduleService.loadScheduleData(shiftFile.getAbsolutePath().replace("\\", "/"), Constants.SHIFT_TABLE_NAME,
                    staffFile.getAbsolutePath().replace("\\", "/"), Constants.STAFF_TABLE_NAME);

            shiftList = shiftRepository.findAll();
            staffList = staffRepository.findAll();

            assertFalse(shiftList.isEmpty());
            assertThat(shiftList.size(), is(13));

            assertFalse(staffList.isEmpty());
            assertThat(staffList.size(), is(13));
        }

        try {
            scheduleService.loadScheduleData("failedTest", Constants.SHIFT_TABLE_NAME,
                    "failedTest", Constants.STAFF_TABLE_NAME);
        }
        catch (Exception e) {
            assertThat(e.getMessage(), is(Constants.ERROR_LOAD_CSV_FILE));

            shiftList = shiftRepository.findAll();
            staffList = staffRepository.findAll();

            assertTrue(shiftList.isEmpty());
            assertTrue(staffList.isEmpty());
        }
    }

    @Test
    public void testGenerateShiftSchedule() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        File staffFile = new File(classLoader.getResource("staff.csv").getFile());
        File shiftFile = new File(classLoader.getResource("shifts.csv").getFile());

        if (shiftFile.exists() && staffFile.exists()) {
            scheduleService.loadScheduleData(shiftFile.getAbsolutePath().replace("\\", "/"), Constants.SHIFT_TABLE_NAME,
                    staffFile.getAbsolutePath().replace("\\", "/"), Constants.STAFF_TABLE_NAME);

            scheduleService.generateShiftSchedule();

            List<ShiftSchedule> shiftScheduleList = shiftScheduleRepository.findAll();
            assertThat(shiftScheduleList.size(), is(13));

            List<Shift> shiftList = shiftRepository.findAll();
            shiftList.forEach( shift -> assertTrue(shift.getAllocateFlag()));
        }
    }
}
