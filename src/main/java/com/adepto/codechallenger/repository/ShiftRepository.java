package com.adepto.codechallenger.repository;

import com.adepto.codechallenger.domain.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

public interface ShiftRepository extends CrudRepository<Shift, String> {

    List<Shift> findAll();

    Shift save(Shift shift);

}
