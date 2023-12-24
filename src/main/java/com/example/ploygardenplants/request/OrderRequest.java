package com.example.ploygardenplants.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long customerId;
    private String customerName;
    private String facebookUrl;
    private String address;
    private Double deposit;
    private List<OrderDetailRequest> orderDetail;
}
