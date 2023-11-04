package com.example.ploygardenplants.response;

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
public class SearchEditCustomerProfileResponse {

    private Long profileID;
    private String profileName;
    private String profileUrl;
    private String addressName;
    private String addressDetail;
    private Long provincesId;
    private Long amphuresId;
    private Long tambonsId;
    private String zipCode;
    private String phoneNumber1;
    private String phoneNumber2;
}
