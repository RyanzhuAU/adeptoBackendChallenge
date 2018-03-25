package com.adepto.codechallenger.controller;

import com.adepto.codechallenger.Constants;
import com.adepto.codechallenger.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * GET /loadShift - load shift from csv file
     *
     * @return status 200
     */
    @RequestMapping(value = "/loadShift", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void loadShift() {
        this.scheduleService.loadScheduleData(Constants.SHIFT_FILE_NAME, Constants.STAFF_FILE_NAME);
    }

    @RequestMapping(value = "/generateShift", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void generateShift() {
        this.scheduleService.generateShiftSchedule();
    }


}
