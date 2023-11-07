package com.example.ploygardenplants.request;

import com.example.ploygardenplants.model.CheckDataOrderDetailRequestModel;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckDataOrderRequest {

    private String customerName;
    private String facebookUrl;

    private List<CheckDataOrderDetailRequestModel> orderDetail;
}
