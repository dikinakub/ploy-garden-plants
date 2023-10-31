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
@Table(name = "order_packaging")
public class OrderPackaging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "op_id")
    private Long opId;

    @Column(name = "op_order_list_id")
    private Long opOrderListId;

    @Column(name = "op_stock_id")
    private Long opStockId;

    @Column(name = "op_shipping_company")
    private String opShippingCompany;

    @Column(name = "op_tracking_number")
    private String opTrackingNumber;

    @Column(name = "op_actual_shipping_price")
    private Double opActualShippingPrice;

    @Basic(optional = false)
    @Column(name = "op_create_by")
    private String opCreateBy;

    @Basic(optional = false)
    @Column(name = "op_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opCreateDatetime;

    @Column(name = "op_update_by")
    private String opUpdateBy;

    @Column(name = "op_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opUpdateDatetime;

}
