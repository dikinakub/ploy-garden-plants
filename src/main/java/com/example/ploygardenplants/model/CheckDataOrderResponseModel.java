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
public class CheckDataOrderResponseModel {

    private String stockName;
    private Long count;
    private Double stockPurchasePrice;
    private Double stockSellingPrice;
    private Long stockRemaining;
    private Long sumRemaining;
    private Double shipping;
    private Double discount;

}
