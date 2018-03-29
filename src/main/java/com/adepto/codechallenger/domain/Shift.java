package com.adepto.codechallenger.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@Data
@Entity
@Table(name = "shift")
public class Shift {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="shift_id")
    private Integer id;

    private String day;

    private String requiredRole;

    private Integer hours;

    private Boolean allocateFlag;
}
