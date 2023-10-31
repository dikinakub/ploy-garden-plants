package com.example.ploygardenplants.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    private Long stockId;
    private String name;
    private Double purchasePrice;
    private Double sellingPrice;
    private Long remaining;
    private String type;
    private String description;
}
