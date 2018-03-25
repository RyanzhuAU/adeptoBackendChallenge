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
@Table(name = "staff")
public class Staff {
    @Id
    @Column(name="staff_id", nullable=false, unique=true)
    private Integer staffId;

    private String validRole;

    private Integer maxHours;

    private String daysUnavailable;
    
}
