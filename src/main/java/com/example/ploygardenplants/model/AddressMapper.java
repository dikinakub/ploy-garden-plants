package com.example.ploygardenplants.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AddressMapper implements RowMapper<AddressModel> {

    @Override
    public AddressModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        AddressModel address = new AddressModel();
        address.setId(rs.getLong("id"));
        address.setAddress(rs.getString("address"));
        return address;
    }
}
