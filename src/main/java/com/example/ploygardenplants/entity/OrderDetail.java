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
@Table(name = "order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "od_id")
    private Long odId;

    @Column(name = "od_order_list_id")
    private Long odOrderListId;

    @Column(name = "od_stock_id")
    private Long odStockId;

    @Column(name = "od_count")
    private Long odCount;

    @Basic(optional = false)
    @Column(name = "od_shipping_price")
    private Double odShippingPrice;

    @Column(name = "od_discount_price")
    private Double odDiscountPrice;
    
    @Column(name = "od_amount")
    private Double odAmount;

    @Basic(optional = false)
    @Column(name = "od_create_by")
    private String odCreateBy;

    @Basic(optional = false)
    @Column(name = "od_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odCreateDatetime;

    @Column(name = "od_update_by")
    private String odUpdateBy;

    @Column(name = "od_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odUpdateDatetime;

}
