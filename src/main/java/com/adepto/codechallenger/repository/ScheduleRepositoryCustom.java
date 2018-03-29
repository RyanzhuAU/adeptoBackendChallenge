package com.adepto.codechallenger.repository;

/**
 * Create by ryan.zhu on 24/03/2018.
 **/

public interface ScheduleRepositoryCustom {

    void loadDataFromCsv(String fileName, String tableName);

    void truncateTable(String tableName);

//    void saveShiftSchedule(Integer shiftId, Integer staffId);

}
