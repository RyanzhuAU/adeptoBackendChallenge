package com.adepto.codechallenger.representation;

import com.adepto.codechallenger.domain.Staff;
import lombok.Data;

/**
 * Created by ryan.zhu on 24/03/2018.
 */

@Data
public class StaffRep {

    private Staff staff;

    private Integer availableHours;

    public StaffRep(Staff staff, Integer availableHours) {
        this.staff = staff;
        this.availableHours = availableHours;
    }

    public void updateAvailableHours(Integer hours) {
        this.availableHours -= hours;
    }

}
