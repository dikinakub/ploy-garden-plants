package com.example.ploygardenplants.response;

import com.example.ploygardenplants.model.CheckDataOrderResponseModel;
import java.util.List;
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
public class CheckDataOrderResponse {

    private String orderReferenceNo;
    private Long customerId;
    private String customerName;
    private String facebookUrl;
    private Double totalPurchasePrice;
    private Double totalSellingPrice;
    private Double totalShippingPrice;
    private Double totalDiscountPrice;

    List<CheckDataOrderResponseModel> orderDetail;

}
