package com.adepto.codechallenger.repository;

import com.adepto.codechallenger.domain.Shift;
import com.adepto.codechallenger.domain.Staff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

public interface StaffRepository extends CrudRepository<Staff, String> {

    List<Staff> findAll();

}
