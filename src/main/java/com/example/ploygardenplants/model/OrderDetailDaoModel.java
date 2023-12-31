package com.example.ploygardenplants.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDaoModel {

    private Long order_id;
    private String order_ref;
    private String status_code;
    private String status_desc;
    private Date order_date;
    private String customer_name;
    private String address;
    private String stock_name;
    private Long count;
    private Double purchase_price;
    private Double selling_price;
    private Double shipping_price;
    private Double discount_price;
    private Double amount;
    private Double total_purchase_price;
    private Double total_selling_price;
    private Double total_shipping_price;
    private Double total_actual_shipping_price;
    private Double total_discount_price;
    private Double total_packaging_price;
    private Double deposit;
    private Double total_amount;
}
