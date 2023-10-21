/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ploygardenplants.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author dikin
 */
@Data
@ToString
@Entity
@Table(name = "customer_profile")
public class CustomerProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cus_id")
    private Long cusId;
    @Basic(optional = false)
    @Column(name = "cus_profile_name")
    private String cusProfileName;
    @Column(name = "cus_profile_url")
    private String cusProfileUrl;
    @Basic(optional = false)
    @Column(name = "cus_create_by")
    private String cusCreateBy;
    @Basic(optional = false)
    @Column(name = "cus_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cusCreateDatetime;
    @Column(name = "cus_update_by")
    private String cusUpdateBy;
    @Column(name = "cus_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cusUpdateDatetime;

}
