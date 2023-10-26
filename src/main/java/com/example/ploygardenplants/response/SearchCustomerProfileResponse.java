package com.example.ploygardenplants.response;

import com.example.ploygardenplants.model.AbstractDataTableResponse;
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
public class SearchCustomerProfileResponse extends AbstractDataTableResponse {

    private Long profileID;
    private String profileName;
    private String profileUrl;
    private String addressName;
    private String addressDetail;
    private String phoneNumber1;
    private String phoneNumber2;
}
