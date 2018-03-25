package com.adepto.codechallenger.service;

import com.adepto.codechallenger.repository.ShiftRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {
    @Autowired
    private ShiftRepository shiftRepository;

    private ScheduleService scheduleService;

    @Before
    public void setup() throws Exception {
        scheduleService = new com.adepto.codechallenger.service.ScheduleServiceImpl(shiftRepository);
    }

    @Test
    public void testLoadScheduleData() throws Exception {
        //TODO write unit test for load shift/staff data

    }

    @Test
    public void testGenerateShiftSchedule() throws Exception {
        //TODO write unit test for generating shift schedule

    }
}
