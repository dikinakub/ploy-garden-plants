package com.example.ploygardenplants.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {

    private Long profileID;
    private String profileName;
    private String profileUrl;
    private String addressName;
    private String addressDetail;
    private Long address;
    private String phoneNumber1;
    private String phoneNumber2;
}
