package com.example.ploygardenplants.model;

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
public class AddressDetailModel {

    private Long addressId;
    private String addressName;
    private String addressDetail;
    private String phoneNumber1;
    private String phoneNumber2;
    private Boolean defaultFlag;

}
