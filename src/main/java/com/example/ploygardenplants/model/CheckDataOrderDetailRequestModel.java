package com.example.ploygardenplants.model;

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
public class CheckDataOrderDetailRequestModel {

    private Long orderId;
    private Long count;
    private Double shipping;
    private Double discount;
}
