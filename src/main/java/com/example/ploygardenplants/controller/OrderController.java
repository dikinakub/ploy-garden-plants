package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.dao.OrderListDaoImpl;
import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.OrderList;
import com.example.ploygardenplants.enums.StatusCode;
import com.example.ploygardenplants.model.SearchModel;
import com.example.ploygardenplants.model.SortModel;
import com.example.ploygardenplants.repository.CustomerAddressRepository;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.OrderDetailRepository;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.request.InquiryRequest;
import com.example.ploygardenplants.request.OrderRequest;
import com.example.ploygardenplants.response.DataTableResponse;
import com.example.ploygardenplants.response.FindOrderResponse;
import com.example.ploygardenplants.response.StatusCodeResponse;
import com.example.ploygardenplants.service.OrderListService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private CustomerProfileRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private OrderListService orderListService;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderListDaoImpl orderListDaoImpl;

    @PostMapping(value = "api/order/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertOrder(@RequestBody OrderRequest orderRequest) {
        return orderListService.insertOrder(orderRequest);
    }

    @PostMapping(value = "api/order/searchOrderList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataTableResponse searchOrderList(@RequestBody InquiryRequest request) {
        List<SortModel> listSort = new ArrayList<>();
        listSort.add(SortModel.builder().field(request.getField()).order(request.getOrder()).build());
        DataTableResponse findDetailByCriteria = orderListDaoImpl.findDetailByCriteria(request.getSearchList(), listSort, 1, Integer.MAX_VALUE);
        return findDetailByCriteria;
    }

    @GetMapping(value = "api/status/statusAll")
    public List<StatusCodeResponse> getStatusAll() {
        List<StatusCodeResponse> res = new ArrayList<>();
        StatusCode[] values = StatusCode.values();
        for (StatusCode value : values) {
            res.add(StatusCodeResponse.builder()
                    .statusCode(value.getStatusCode())
                    .statusDescEn(value.getStatusDescEn())
                    .statusDescTh(value.getStatusDescTh())
                    .build());
        }
        return res;
    }
}
