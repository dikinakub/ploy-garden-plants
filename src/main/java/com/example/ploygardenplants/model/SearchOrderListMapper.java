package com.example.ploygardenplants.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;

public class SearchOrderListMapper implements RowMapper<SearchOrderListModel> {

    @Override
    public SearchOrderListModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        SearchOrderListModel list = new SearchOrderListModel();
        list.setReference_no(rs.getString("reference_no"));
        list.setCustomer_name(rs.getString("customer_name"));
        list.setAddress_id(rs.getLong("address_id"));
        list.setPurchase_price(Optional.ofNullable(rs.getDouble("purchase_price")).orElse(Double.valueOf(0)));
        list.setSelling_price(Optional.ofNullable(rs.getDouble("selling_price")).orElse(Double.valueOf(0)));
        list.setShipping_price(Optional.ofNullable(rs.getDouble("shipping_price")).orElse(Double.valueOf(0)));
        list.setDiscount_price(Optional.ofNullable(rs.getDouble("discount_price")).orElse(Double.valueOf(0)));
        list.setDeposit(Optional.ofNullable(rs.getDouble("deposit")).orElse(Double.valueOf(0)));
        list.setAmount(Optional.ofNullable(rs.getDouble("amount")).orElse(Double.valueOf(0)));
        list.setStatus_code(rs.getString("status_code"));
        list.setStatus_desc(rs.getString("status_desc"));
        list.setCreate_datetime(rs.getDate("create_datetime") == null ? null : rs.getDate("create_datetime"));
        return list;
    }
}
