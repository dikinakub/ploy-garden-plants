package com.example.ploygardenplants.service;

import com.example.ploygardenplants.constant.CommonConstant;
import static com.example.ploygardenplants.constant.CommonConstant.DASH;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.Stock;
import com.example.ploygardenplants.model.CheckDataOrderDetailRequestModel;
import com.example.ploygardenplants.model.CheckDataOrderResponseModel;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.repository.StockRepository;
import com.example.ploygardenplants.request.CheckDataOrderRequest;
import com.example.ploygardenplants.response.CheckDataOrderResponse;
import com.example.ploygardenplants.util.DateUtil;
import static com.example.ploygardenplants.util.DateUtil.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderListService {

    public static final String PREFIX_PAYMENT_REF = "OR";

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private CustomerProfileRepository customerRepository;

    @Autowired
    private StockRepository stockRepository;

    public CheckDataOrderResponse checkOrderData(CheckDataOrderRequest request) {

        CheckDataOrderResponse response = new CheckDataOrderResponse();
        List<CheckDataOrderResponseModel> orderDetail = new ArrayList<>();

        response.setOrderReferenceNo("OR-20231107-0001");
        List<CustomerProfile> findByCusProfileNameLike = customerRepository.findByCusProfileNameLike(request.getCustomerName().toUpperCase());
        if (!findByCusProfileNameLike.isEmpty()) {
            response.setCustomerId(findByCusProfileNameLike.get(0).getCusId());
            response.setCustomerName(findByCusProfileNameLike.get(0).getCusProfileName());
            response.setFacebookUrl(findByCusProfileNameLike.get(0).getCusProfileUrl());
        } else {
            response.setCustomerName(request.getCustomerName());
            response.setFacebookUrl(request.getFacebookUrl());
        }

        List<Stock> stocks = stockRepository.findByStockTypeAndStockIsActiveOrderByStockName("TREE", "Y");
        for (CheckDataOrderDetailRequestModel detailReq : request.getOrderDetail()) {
            Optional<Stock> findStock = stocks.stream().filter(i -> i.getStockId().equals(detailReq.getOrderId())).findFirst();
            if (findStock.isPresent()) {
                Stock stock = findStock.get();
                CheckDataOrderResponseModel orderDetailRes = new CheckDataOrderResponseModel();
                orderDetailRes.setStockName(stock.getStockName());
                orderDetailRes.setCount(detailReq.getCount());
                orderDetailRes.setStockPurchasePrice(stock.getStockPurchasePrice());
                orderDetailRes.setStockSellingPrice(stock.getStockSellingPrice());
                orderDetailRes.setStockRemaining(stock.getStockRemaining());
                orderDetailRes.setSumRemaining(stock.getStockRemaining() - detailReq.getCount());
                orderDetailRes.setShipping(detailReq.getShipping());
                orderDetailRes.setDiscount(detailReq.getDiscount());
                orderDetail.add(orderDetailRes);
            }
        }
        response.setOrderDetail(orderDetail);
        response.setTotalPurchasePrice(orderDetail.stream().mapToDouble(i -> i.getStockPurchasePrice()).sum());
        response.setTotalSellingPrice(orderDetail.stream().mapToDouble(i -> i.getStockSellingPrice()).sum());
        response.setTotalShippingPrice(orderDetail.stream().mapToDouble(i -> i.getShipping()).sum());
        response.setTotalDiscountPrice(orderDetail.stream().mapToDouble(i -> i.getDiscount()).sum());

        return response;
    }

    public String generateOrderRefNo() {
        Long runningNo = getNextRunningNoOrder();
        String currentDate = dateUtil.parseDateFormat(LocalDateTime.now(), YYYYMMDD_DATE_PATTERN);
        String runningNoString = String.format("%4s", String.valueOf(runningNo)).replace(" ", "0");
        // Ex. PMT-20210726-00001
        return new StringBuilder(PREFIX_PAYMENT_REF)
                .append(DASH)
                .append(currentDate)
                .append(DASH)
                .append(runningNoString).toString();
    }

    private Long getNextRunningNoOrder() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        String currentDateISO8601 = dateUtil.parseDateFormat(currentLocalDateTime, ISO_8601_DATE);
        String currentDateYYYYMMDD = dateUtil.parseDateFormat(currentLocalDateTime, YYYYMMDD_DATE_PATTERN);
        // - - - - > Find lastest create date of current date in PAYMENT_PRE_GROUP.
        String paymentRef = orderListRepository.findLastestByCurrentDateISO8601(currentDateISO8601);
        if (null == paymentRef) {
            // - - - - > Reset running no. if current date != lastest create date from PAYMENT_PRE_GROUP.
            orderListRepository.resetRunningNo();
            return orderListRepository.nextVal();

        } else {
            String[] paymentRefSplit = paymentRef.split(CommonConstant.DASH);
            if (!paymentRefSplit[1].equals(currentDateYYYYMMDD)) {
                // - - - - > Reset running no. if current date != lastest create date from PAYMENT_PRE_GROUP.
                orderListRepository.resetRunningNo();
                return orderListRepository.nextVal();
            } else {
                // Return nextval of PAYMENT_VOUCHER_RUNNING_NO_seq.
                return orderListRepository.nextVal();
            }
        }
    }
}
