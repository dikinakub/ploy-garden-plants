package com.example.ploygardenplants.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

public class OrderDetailDaoMapper implements RowMapper<OrderDetailDaoModel> {

    @Override
    public OrderDetailDaoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDetailDaoModel detail = new OrderDetailDaoModel();
        detail.setOrder_id(rs.getLong("order_id"));
        detail.setOrder_ref(rs.getString("order_ref"));
        detail.setStatus_code(rs.getString("status_code"));
        detail.setStatus_desc(rs.getString("status_desc"));
        detail.setOrder_date(rs.getDate("order_date") == null ? null : rs.getDate("order_date"));
        detail.setCustomer_name(rs.getString("customer_name"));
        detail.setAddress(rs.getString("address"));
        detail.setStock_name(rs.getString("stock_name"));
        detail.setCount(rs.getLong("count"));
        detail.setPurchase_price(Optional.ofNullable(rs.getDouble("purchase_price")).orElse(Double.valueOf(0)));
        detail.setSelling_price(Optional.ofNullable(rs.getDouble("selling_price")).orElse(Double.valueOf(0)));
        detail.setShipping_price(Optional.ofNullable(rs.getDouble("shipping_price")).orElse(Double.valueOf(0)));
        detail.setDiscount_price(Optional.ofNullable(rs.getDouble("discount_price")).orElse(Double.valueOf(0)));
        detail.setAmount(Optional.ofNullable(rs.getDouble("amount")).orElse(Double.valueOf(0)));
        detail.setTotal_purchase_price(Optional.ofNullable(rs.getDouble("total_purchase_price")).orElse(Double.valueOf(0)));
        detail.setTotal_selling_price(Optional.ofNullable(rs.getDouble("total_selling_price")).orElse(Double.valueOf(0)));
        detail.setTotal_shipping_price(Optional.ofNullable(rs.getDouble("total_shipping_price")).orElse(Double.valueOf(0)));
        detail.setTotal_actual_shipping_price(Optional.ofNullable(rs.getDouble("total_actual_shipping_price")).orElse(Double.valueOf(0)));
        detail.setTotal_discount_price(Optional.ofNullable(rs.getDouble("total_discount_price")).orElse(Double.valueOf(0)));
        detail.setTotal_packaging_price(Optional.ofNullable(rs.getDouble("total_packaging_price")).orElse(Double.valueOf(0)));
        detail.setDeposit(Optional.ofNullable(rs.getDouble("deposit")).orElse(Double.valueOf(0)));
        detail.setTotal_amount(Optional.ofNullable(rs.getDouble("total_amount")).orElse(Double.valueOf(0)));
        return detail;
    }
}
