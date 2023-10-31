package com.example.ploygardenplants.service;

import com.example.ploygardenplants.constant.CommonConstant;
import static com.example.ploygardenplants.constant.CommonConstant.DASH;
import com.example.ploygardenplants.repository.OrderListRepository;
import com.example.ploygardenplants.util.DateUtil;
import static com.example.ploygardenplants.util.DateUtil.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderListService {

    public static final String PREFIX_PAYMENT_REF = "OR";

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private OrderListRepository orderListRepository;

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

    public Long getNextRunningNoOrder() {
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
