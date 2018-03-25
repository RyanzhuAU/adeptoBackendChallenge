package com.adepto.codechallenger.repository;

import com.adepto.codechallenger.domain.Shift;
import com.adepto.codechallenger.domain.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

@Repository
public interface ShiftScheduleRepository extends CrudRepository<Shift, String> {

    ShiftSchedule save(ShiftSchedule shiftSchedule);

}
