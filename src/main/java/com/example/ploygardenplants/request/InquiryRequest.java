package com.example.ploygardenplants.request;

import com.example.ploygardenplants.enums.SortType;
import com.example.ploygardenplants.model.SearchModel;
import com.example.ploygardenplants.model.SortModel;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class InquiryRequest {

    private Integer page;

    private Integer pageSize;

    private List<SearchModel> searchList;

    //Sort
    private String field;
    private String order;

    @JsonIgnore
    private Boolean isInvert;

    // handle field no. for invert sort field
//    public void setSortList(List<SortModel> sortList) {
//        this.isInvert = false;
//        for (SortModel s : sortList) {
//            if (s.getField().equalsIgnoreCase("no") && s.getOrderSafe().equals(SortType.DESC)) {
//                this.isInvert = true;
//            }
//        }
//        List<SortModel> tmp = new ArrayList<>();
//        for (SortModel s : sortList) {
//            if (s.getField().equalsIgnoreCase("no")) {
//                continue;
//            }
//            if (Boolean.TRUE.equals(isInvert)) {
//                s.setOrder(SortType.invert(s.getOrderSafe()).getValue());  // invert sort
//            }
//            tmp.add(s);
//        }
//        this.sortList = tmp;
//    }
}
