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
public class StatusCodeResponse {

    private String statusCode;
    private String statusDescEn;
    private String statusDescTh;
}
