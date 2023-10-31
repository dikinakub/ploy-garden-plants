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

@Data
@ToString
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stock_id")
    private Long stockId;
    @Basic(optional = false)
    @Column(name = "stock_name")
    private String stockName;
    @Basic(optional = false)
    @Column(name = "stock_purchase_price")
    private Double stockPurchasePrice;
    @Basic(optional = false)
    @Column(name = "stock_selling_price")
    private Double stockSellingPrice;
    @Basic(optional = false)
    @Column(name = "stock_remaining")
    private Long stockRemaining;
    @Basic(optional = false)
    @Column(name = "stock_type")
    private String stockType;
    @Column(name = "stock_description")
    private String stockDescription;
    @Basic(optional = false)
    @Column(name = "stock_is_active")
    private String stockIsActive;
    @Basic(optional = false)
    @Column(name = "stock_create_by")
    private String stockCreateBy;
    @Basic(optional = false)
    @Column(name = "stock_create_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockCreateDatetime;
    @Column(name = "stock_update_by")
    private String stockUpdateBy;
    @Column(name = "stock_update_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockUpdateDatetime;

}
