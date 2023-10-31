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
@Table(name = "order_list")
public class OrderList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ol_id")
    private Long olId;

    @Column(name = "ol_customer_id")
    private Long olCustomerId;

    @Basic(optional = false)
    @Column(name = "ol_reference_no")
    private String olReferenceNo;

    @Basic(optional = false)
    @Column(name = "ol_total_purchase_price")
    private Double olTotalPurchasePrice;

    @Basic(optional = false)
    @Column(name = "ol_total_selling_price")
    private Double olTotalSellingPrice;

    @Basic(optional = false)
    @Column(name = "ol_total_shipping_price")
    private Double olTotalShippingPrice;

    @Column(name = "ol_total_discount_price")
    private Double olTotalDiscountPrice;

    @Column(name = "ol_total_actual_shipping_price")
    private Double olTotalActualShippingPrice;

    @Column(name = "ol_total_packaging_price")
    private Double olTotalPackagingPrice;

    @Basic(optional = false)
    @Column(name = "ol_ststus_code")
    private String olStstusCode;

    @Column(name = "ol_status_desc")
    private String olStatusDesc;

    @Basic(optional = false)
    @Column(name = "ol_create_by")
    private String olCreateBy;

    @Basic(optional = false)
    @Column(name = "ol_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date olCreateDatetime;

    @Column(name = "ol_update_by")
    private String olUpdateBy;

    @Column(name = "ol_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date olUpdateDatetime;

}
