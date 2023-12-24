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
@Table(name = "order_history")
public class OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "oh_id")
    private Long ohId;

    @Column(name = "oh_order_list_id")
    private Long ohOrderListId;

    @Column(name = "oh_action")
    private String ohAction;

    @Column(name = "oh_status_desc")
    private String ohStatusDesc;

    @Basic(optional = false)
    @Column(name = "oh_create_by")
    private String ohCreateBy;

    @Basic(optional = false)
    @Column(name = "oh_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ohCreateDatetime;

    @Column(name = "oh_update_by")
    private String ohUpdateBy;

    @Column(name = "oh_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ohUpdateDatetime;

}
