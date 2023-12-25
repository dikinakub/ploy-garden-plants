package com.example.ploygardenplants.service;

import com.example.ploygardenplants.constant.CommonConstant;
import static com.example.ploygardenplants.constant.CommonConstant.DASH;
import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.OrderDetail;
import com.example.ploygardenplants.entity.OrderHistory;
import com.example.ploygardenplants.entity.OrderList;
import com.example.ploygardenplants.entity.Stock;
import com.example.ploygardenplants.enums.StatusCode;
import com.example.ploygardenplants.repository.CustomerAddressRepository;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.OrderDetailRepository;
import com.example.ploygardenplants.repository.OrderHistoryRepository;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.repository.StockRepository;
import com.example.ploygardenplants.request.OrderDetailRequest;
import com.example.ploygardenplants.request.OrderRequest;
import com.example.ploygardenplants.util.DateUtil;
import static com.example.ploygardenplants.util.DateUtil.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderListService {

    public static final String PREFIX_PAYMENT_REF = "OR";

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private CustomerProfileRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ILoggerService loggerService;

    @Transactional
    public ResponseEntity<String> insertOrder(OrderRequest orderRequest) {

        String user = "SYSTEM";
        Date date = new Date();
        try {
            List<Stock> findByStockIdIn = new ArrayList<>();
            if (orderRequest.getOrderDetail() != null && !orderRequest.getOrderDetail().isEmpty()) {
                List<Long> stockIds = new ArrayList<>();
                orderRequest.getOrderDetail().forEach(d -> stockIds.add(d.getOrderId()));
                findByStockIdIn = stockRepository.findByStockIdIn(stockIds);

                List<Stock> collect = findByStockIdIn.stream().filter(s -> s.getStockRemaining() < 1).collect(Collectors.toList());
                if (collect != null && !collect.isEmpty()) {
                    List<String> name = new ArrayList<>();
                    collect.forEach(s -> name.add(s.getStockName()));
                    return new ResponseEntity<>(String.format("%s คงเหลือ 0", name.toString()), HttpStatus.BAD_REQUEST);
                }
            }

            if (orderRequest.getCustomerId() == null || orderRequest.getCustomerId().equals("")) {
                List<CustomerProfile> findByCusProfileName = customerRepository.findByCusProfileName(orderRequest.getCustomerName());
                if (!findByCusProfileName.isEmpty()) {
                    return new ResponseEntity<>("Facebook name is duplicate. (ซ้ำ)", HttpStatus.BAD_REQUEST);
                }
                CustomerProfile customerProfile = new CustomerProfile();
                customerProfile.setCusProfileName(orderRequest.getCustomerName());
                customerProfile.setCusCreateBy(user);
                customerProfile.setCusCreateDatetime(date);
                CustomerProfile save = customerRepository.save(customerProfile);
                orderRequest.setCustomerId(save.getCusId());

                CustomerAddress customerAddress = new CustomerAddress();
                customerAddress.setAddCusId(save.getCusId());
                customerAddress.setAddName("เพิ่มที่อยู่");
                customerAddress.setAddIsActive("Y");
                customerAddress.setDefaultFlag(Boolean.TRUE);
                customerAddress.setAddCreateBy(user);
                customerAddress.setAddCreateDatetime(date);
                customerAddressRepository.save(customerAddress);
            }

            if (orderRequest.getOrderDetail() != null && !orderRequest.getOrderDetail().isEmpty()) {
                Double totalPurchase = Double.valueOf(0);
                Double totalSelling = Double.valueOf(0);
                Double totalShipping = Double.valueOf(0);
                Double totalDiscount = Double.valueOf(0);
                Double totalAmount = Double.valueOf(0);
                Double deposit = orderRequest.getDeposit() != null ? orderRequest.getDeposit() : Double.valueOf(0);

                List<OrderDetail> orderDetailSaves = new ArrayList<>();
                for (OrderDetailRequest orderDetail : orderRequest.getOrderDetail()) {
                    Optional<Stock> findStockId = findByStockIdIn.stream().filter(s -> s.getStockId().equals(orderDetail.getOrderId())).findFirst();
                    if (findStockId.isPresent()) {
                        Stock stock = findStockId.get();

                        totalPurchase = totalPurchase + stock.getStockPurchasePrice();
                        totalSelling = totalSelling + stock.getStockSellingPrice();
                        totalShipping = totalShipping + orderDetail.getShipping();
                        totalDiscount = totalDiscount + orderDetail.getDiscount();
                        Double amount = stock.getStockSellingPrice() * orderDetail.getCount() + orderDetail.getShipping() - orderDetail.getDiscount();
                        totalAmount = totalAmount + amount;

                        OrderDetail orderDetailSave = new OrderDetail();
                        orderDetailSave.setOdStockId(stock.getStockId());
                        orderDetailSave.setOdCount(orderDetail.getCount());
                        orderDetailSave.setOdShippingPrice(orderDetail.getShipping());
                        orderDetailSave.setOdDiscountPrice(orderDetail.getDiscount());
                        orderDetailSave.setOdAmount(amount);
                        orderDetailSave.setOdCreateBy(user);
                        orderDetailSave.setOdCreateDatetime(date);
                        orderDetailSaves.add(orderDetailSave);
                    }
                }

                OrderList order = new OrderList();
                String generateOrderRefNo = generateOrderRefNo();
                order.setOlReferenceNo(generateOrderRefNo);
                order.setOlCustomerId(orderRequest.getCustomerId());
                order.setOlTotalPurchasePrice(totalPurchase);
                order.setOlTotalSellingPrice(totalSelling);
                order.setOlTotalShippingPrice(totalShipping);
                order.setOlTotalDiscountPrice(totalDiscount);
                order.setOlTotalAmount(totalAmount);
                order.setOlDeposit(deposit);
                order.setOlStatusCode(StatusCode.STATUS01.getStatusCode());
                order.setOlStatusDesc(StatusCode.STATUS01.getStatusDescEn());
                order.setOlCreateBy(user);
                order.setOlCreateDatetime(date);
                OrderList orderList = orderListRepository.save(order);

                orderDetailSaves.forEach(d -> d.setOdOrderListId(orderList.getOlId()));
                orderDetailRepository.saveAll(orderDetailSaves);

                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setOhOrderListId(orderList.getOlId());
                orderHistory.setOhAction(generateOrderRefNo);
                orderHistory.setOhStatusDesc(StatusCode.STATUS01.getStatusDescEn());
                orderHistory.setOhCreateBy(user);
                orderHistory.setOhCreateDatetime(date);
                orderHistoryRepository.save(orderHistory);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            loggerService.printStackTrace(user, e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    public String generateOrderRefNo() {
        Long runningNo = getNextRunningNoOrder();
        String currentDate = dateUtil.parseDateFormat(LocalDateTime.now(), YYYYMMDD_DATE_PATTERN);
        String runningNoString = String.format("%4s", String.valueOf(runningNo)).replace(" ", "0");
        // Ex. OR-20231224-0001
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
