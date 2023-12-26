package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.OrderList;
import com.example.ploygardenplants.repository.CustomerAddressRepository;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.OrderDetailRepository;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.request.OrderRequest;
import com.example.ploygardenplants.response.FindOrderResponse;
import com.example.ploygardenplants.service.OrderListService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @PostMapping(value = "api/order/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertOrder(@RequestBody OrderRequest orderRequest) {
        return orderListService.insertOrder(orderRequest);
    }

    @GetMapping("api/order/findAll")
    public List<FindOrderResponse> findAll() {
        List<FindOrderResponse> response = new ArrayList<>();

        List<OrderList> findAll = orderListRepository.findAll();
        int i = 0;
        for (OrderList orderList : findAll) {
            Optional<CustomerProfile> customerOptional = customerRepository.findById(orderList.getOlCustomerId());
            if (customerOptional.isPresent()) {
                CustomerProfile customer = customerOptional.get();
                List<CustomerAddress> findByAddCusId = customerAddressRepository.findByAddCusIdAndDefaultFlagAndAddIsActive(customer.getCusId(), true, "Y");

                FindOrderResponse res = new FindOrderResponse();
                res.setCustomerName(customer.getCusProfileName());
                res.setNo(++i);
                res.setOrder(orderList);
                if (findByAddCusId != null && !findByAddCusId.isEmpty()) {
                    res.setCustomerAddressId(findByAddCusId.get(0).getAddId());
                }
                response.add(res);
            }
        }

        return response;
    }
}
