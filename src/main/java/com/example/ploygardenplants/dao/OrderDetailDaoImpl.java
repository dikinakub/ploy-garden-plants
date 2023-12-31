package com.example.ploygardenplants.dao;

import com.example.ploygardenplants.model.OrderDetailDaoMapper;
import com.example.ploygardenplants.model.OrderDetailDaoModel;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository("OrderDetailDaoImpl")
public class OrderDetailDaoImpl {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<OrderDetailDaoModel> findOrderDetailByOrderRef(String orderRef) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM order_detail_view WHERE order_ref in ('").append(orderRef).append("')");
        return namedParameterJdbcTemplate.query(query.toString(), new OrderDetailDaoMapper());
    }
}
