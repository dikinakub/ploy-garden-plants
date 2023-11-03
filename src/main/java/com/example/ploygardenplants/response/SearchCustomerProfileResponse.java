package com.example.ploygardenplants.response;

import com.example.ploygardenplants.model.AddressDetailModel;
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
public class SearchCustomerProfileResponse extends AbstractDataTableResponse {

    private Long profileID;
    private String profileName;
    private String profileUrl;
    private List<AddressDetailModel> addressDetailList;
    
}
