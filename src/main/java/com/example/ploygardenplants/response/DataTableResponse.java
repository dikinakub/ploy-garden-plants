package com.example.ploygardenplants.response;

import com.example.ploygardenplants.model.SortModel;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
public class DataTableResponse {

    @ToString.Exclude
    private List<? extends AbstractDataTableResponse> items;

    @JsonIgnore
    private Integer page;

    @JsonIgnore
    private Integer pageSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalItem;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalRecord;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String totalNetAmount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String currency;

    @JsonIgnore //optional : use for running no.
    private Boolean invert;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SortModel> sortList;

    public static class DataTableResponseBuilder {

        public DataTableResponse buildWithNo() {
            DataTableResponse result = build();
            Integer no = ((page - 1) * pageSize) + 1;
            Integer runningNo = 1;
            if (invert != null && invert) {
                no = Math.toIntExact(totalRecord) - ((page - 1) * pageSize);
                runningNo = -1;
            }
            for (AbstractDataTableResponse item : items) {
                item.setNo(no);
                no = no + runningNo;
            }
            return result;
        }
    }

}
