package com.example.ploygardenplants.response;

import com.example.ploygardenplants.entity.OrderList;
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
public class FindOrderResponse extends AbstractDataTableResponse {

    private Long customerAddressId;
    private String CustomerName;
    private OrderList order;

}
