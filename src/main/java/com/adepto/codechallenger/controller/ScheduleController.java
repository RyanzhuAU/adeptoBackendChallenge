package com.adepto.codechallenger.controller;

import com.adepto.codechallenger.Constants;
import com.adepto.codechallenger.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * GET /loadShift - load shift from csv file
     *
     * @return status 200
     */
    @RequestMapping(value = "/loadShift", method = RequestMethod.GET)
    public ResponseEntity loadShift() {
        try {
            this.scheduleService.loadScheduleData(Constants.SHIFT_FILE_NAME, Constants.SHIFT_TABLE_NAME, Constants.STAFF_FILE_NAME, Constants.STAFF_TABLE_NAME);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/generateShift", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity generateShift() {
        try {
            this.scheduleService.generateShiftSchedule();
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
