package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.dao.OrderDetailDaoImpl;
import com.example.ploygardenplants.dao.OrderListDaoImpl;
import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.OrderDetail;
import com.example.ploygardenplants.entity.OrderList;
import com.example.ploygardenplants.entity.Stock;
import com.example.ploygardenplants.enums.StatusCode;
import com.example.ploygardenplants.model.OrderDetailDaoModel;
import com.example.ploygardenplants.model.SearchModel;
import com.example.ploygardenplants.model.SearchOrderDetailModel;
import com.example.ploygardenplants.model.SortModel;
import com.example.ploygardenplants.repository.CustomerAddressRepository;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.OrderDetailRepository;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.repository.StockRepository;
import com.example.ploygardenplants.request.InquiryRequest;
import com.example.ploygardenplants.request.OrderRequest;
import com.example.ploygardenplants.response.DataTableResponse;
import com.example.ploygardenplants.response.FindOrderResponse;
import com.example.ploygardenplants.response.OrderDetailResponse;
import com.example.ploygardenplants.response.SearchCustomerProfileResponse;
import com.example.ploygardenplants.response.StatusCodeResponse;
import com.example.ploygardenplants.service.OrderListService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CustomerController customerController;

    @Autowired
    private OrderDetailDaoImpl orderDetailDaoImpl;

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

    @GetMapping(value = "api/order/detail/{orderRef}")
    public OrderDetailResponse orderDetail(@PathVariable String orderRef) {
        OrderDetailResponse res = new OrderDetailResponse();
        List<OrderDetailDaoModel> findOrderDetailByOrderRef = orderDetailDaoImpl.findOrderDetailByOrderRef(orderRef);
        if (findOrderDetailByOrderRef != null && !findOrderDetailByOrderRef.isEmpty()) {
            Map<String, List<OrderDetailDaoModel>> groupByOrderRefMap = findOrderDetailByOrderRef.stream().collect(Collectors.groupingBy(d -> d.getOrder_ref()));
            for (Map.Entry<String, List<OrderDetailDaoModel>> mapCurr : groupByOrderRefMap.entrySet()) {
                OrderDetailDaoModel orderList = mapCurr.getValue().get(0);
                res.setOrder_id(orderList.getOrder_id());
                res.setOrder_ref(orderList.getOrder_ref());
                res.setStatus_code(orderList.getStatus_code());
                StatusCode fromStatusCode = StatusCode.fromStatusCode(orderList.getStatus_code());
                res.setStatus_desc(fromStatusCode.getStatusDescEn() + " (" + fromStatusCode.getStatusDescTh() + ")");
                res.setOrder_date(orderList.getOrder_date());
                res.setCustomer_name(orderList.getCustomer_name());
                res.setAddress(orderList.getAddress());
                res.setTotal_purchase_price(orderList.getTotal_purchase_price());
                res.setTotal_selling_price(orderList.getTotal_selling_price());
                res.setTotal_shipping_price(orderList.getTotal_shipping_price());
                res.setTotal_actual_shipping_price(orderList.getTotal_actual_shipping_price());
                res.setTotal_discount_price(orderList.getTotal_discount_price());
                res.setTotal_packaging_price(orderList.getTotal_packaging_price());
                res.setDeposit(orderList.getDeposit());
                res.setTotal_amount(orderList.getTotal_amount());

                List<SearchOrderDetailModel> orderDetails = new ArrayList<>();
                for (OrderDetailDaoModel detail : mapCurr.getValue()) {
                    SearchOrderDetailModel detailRes = new SearchOrderDetailModel();
                    detailRes.setStock_name(detail.getStock_name());
                    detailRes.setCount(detail.getCount());
                    detailRes.setPurchase_price(detail.getPurchase_price());
                    detailRes.setSelling_price(detail.getSelling_price());
                    detailRes.setShipping_price(detail.getShipping_price());
                    detailRes.setDiscount_price(detail.getDiscount_price());
                    detailRes.setAmount(detail.getAmount());
                    orderDetails.add(detailRes);
                }
                res.setOrderDetails(orderDetails);
            }
        }
        return res;
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

    @GetMapping(value = "api/status/statusUpdate")
    public List<StatusCodeResponse> getStatusUpdate() {
        List<StatusCodeResponse> res = new ArrayList<>();
        List<StatusCode> status = List.of(
                StatusCode.STATUS01, 
                StatusCode.STATUS03, 
                StatusCode.STATUS04, 
                StatusCode.STATUS06, 
                StatusCode.STATUS07);
        for (StatusCode value : status) {
            res.add(StatusCodeResponse.builder()
                    .statusCode(value.getStatusCode())
                    .statusDescEn(value.getStatusDescEn())
                    .statusDescTh(value.getStatusDescTh())
                    .build());
        }
        return res;
    }
}
