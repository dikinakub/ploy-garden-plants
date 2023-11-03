package com.example.ploygardenplants.response;

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
public class SearchStockResponse extends AbstractDataTableResponse {

    private Long stockId;
    private String stockName;
    private String stockPurchasePrice;
    private String stockSellingPrice;
    private String stockRemaining;
    private String stockType;
    private String stockDescription;
    
}
