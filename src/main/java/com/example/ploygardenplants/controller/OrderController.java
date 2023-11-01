package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.request.StockRequest;
import com.example.ploygardenplants.service.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderListService orderListService;

    @PostMapping(value = "api/order/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveOrder(@RequestBody StockRequest request) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
