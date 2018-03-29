package com.adepto.codechallenger.service;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
public interface ScheduleService {

    void loadScheduleData(String shiftFileName, String shiftTableName, String staffFileName, String staffTableName) throws Exception;

    void generateShiftSchedule();
}
