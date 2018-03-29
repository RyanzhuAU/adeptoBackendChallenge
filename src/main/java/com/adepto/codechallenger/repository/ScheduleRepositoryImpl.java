package com.adepto.codechallenger.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Create by ryan.zhu on 24/03/2018.
 **/
@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void loadDataFromCsv(String fileName, String tableName) {
        String sql = "LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE "+ tableName + "\n" +
                "FIELDS TERMINATED BY ',' \n" +
                "ENCLOSED BY '\"' \n" +
                "LINES TERMINATED BY '\\r\\n'\n" +
                "IGNORE 1 LINES;";

        jdbcTemplate.execute(sql);
    }

    public void truncateTable(String tableName) {
        String sql = "DELETE FROM " + tableName;

        jdbcTemplate.execute(sql);
    }

//    public void saveShiftSchedule(Integer shiftId, Integer staffId){
//        String sql = "INSERT INTO shift_schedule\n" +
//                "(shift_id, staff_id)\n" +
//                "VALUES\n" +
//                "(" + shiftId + "," + staffId + ");";
//
//        jdbcTemplate.execute(sql);
//
//    }
}
