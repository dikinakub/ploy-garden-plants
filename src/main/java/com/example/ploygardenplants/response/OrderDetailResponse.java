package com.example.ploygardenplants.response;

import com.example.ploygardenplants.model.SearchOrderDetailModel;
import java.util.Date;
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
public class OrderDetailResponse {

    private Long order_id;
    private String order_ref;
    private String status_code;
    private String status_desc;
    private Date order_date;
    private String customer_name;
    private String address;
    private Double total_purchase_price;
    private Double total_selling_price;
    private Double total_shipping_price;
    private Double total_actual_shipping_price;
    private Double total_discount_price;
    private Double total_packaging_price;
    private Double deposit;
    private Double total_amount;

    private List<SearchOrderDetailModel> orderDetails;

}
