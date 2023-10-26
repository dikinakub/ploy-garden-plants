package com.example.ploygardenplants.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String facebookName;
    private String facebookUrl;
    private String name;
    private String addressDetail;
    private Long address;
    private String phoneNumber1;
    private String phoneNumber2;

}
