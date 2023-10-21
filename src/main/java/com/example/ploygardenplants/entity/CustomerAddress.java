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
@Table(name = "customer_address")
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "add_id")
    private Long addId;
    @Basic(optional = false)
    @Column(name = "add_name")
    private String addName;
    @Basic(optional = false)
    @Column(name = "add_address_detail")
    private String addAddressDetail;
    @Basic(optional = false)
    @Column(name = "add_phone_number")
    private String addPhoneNumber;
    @Basic(optional = false)
    @Column(name = "add_is_active")
    private String addIsActive;
    @Basic(optional = false)
    @Column(name = "add_create_by")
    private String addCreateBy;
    @Basic(optional = false)
    @Column(name = "add_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addCreateDatetime;
    @Column(name = "add_update_by")
    private String addUpdateBy;
    @Column(name = "add_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addUpdateDatetime;
    @Column(name = "add_cus_id")
    private Long addCusId;
    @Column(name = "add_tambons_id")
    private Long addTambonsId;

}