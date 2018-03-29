package com.adepto.codechallenger.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="staff_id", nullable=false, unique=true)
    private Integer id;

    private String validRole;

    private Integer maxHours;

    private String daysUnavailable;
    
}
