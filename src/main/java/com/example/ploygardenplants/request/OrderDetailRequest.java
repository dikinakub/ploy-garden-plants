package com.example.ploygardenplants.request;

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
public class OrderDetailRequest {
    private Long orderId;
    private Long count;
    private Double shipping;
    private Double discount;
    
}
