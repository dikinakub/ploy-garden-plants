package com.example.ploygardenplants.model;

import com.example.ploygardenplants.response.AbstractDataTableResponse;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchOrderListModel extends AbstractDataTableResponse {

    private String reference_no;
    private String customer_name;
    private Long address_id;
    private Double purchase_price;
    private Double selling_price;
    private Double shipping_price;
    private Double discount_price;
    private Double deposit;
    private Double amount;
    private String status_code;
    private String status_desc;
    private Date create_datetime;
}
