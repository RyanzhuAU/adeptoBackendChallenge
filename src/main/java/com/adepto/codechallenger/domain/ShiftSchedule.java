package com.adepto.codechallenger.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@Data
@Entity
@Table(name = "shift_schedule")
public class ShiftSchedule {
    @Id
    private Integer scheduleId;

    @ManyToOne
    @JoinColumn(name="shift_id")
    private Shift shift;

    @ManyToOne
    @JoinColumn(name="staff_id")
    private Staff staff;

    public ShiftSchedule (Shift shift, Staff staff) {
        this.shift = shift;
        this.staff = staff;
    }
}
