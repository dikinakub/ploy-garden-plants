package com.example.ploygardenplants.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchOrderDetailModel {

    private String stock_name;
    private Long count;
    private Double purchase_price;
    private Double selling_price;
    private Double shipping_price;
    private Double discount_price;
    private Double amount;
}
