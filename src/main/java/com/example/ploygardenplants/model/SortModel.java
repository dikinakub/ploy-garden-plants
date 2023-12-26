package com.example.ploygardenplants.model;

import com.example.ploygardenplants.enums.SortType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SortModel {

    private String field;
    private String order;

    @JsonIgnore
    public SortType getOrderSafe() {
        return SortType.fromValue(this.order);
    }
}
