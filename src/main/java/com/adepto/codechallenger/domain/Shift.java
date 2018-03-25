package com.adepto.codechallenger.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@Data
@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @Column(name="shift_id")
    private Integer shiftId;

    private String day;

    private String requiredRole;

    private Integer hours;

    private Boolean allocateFlag;
}
