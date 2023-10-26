package com.example.ploygardenplants.dao;


import com.example.ploygardenplants.model.AddressMapper;
import com.example.ploygardenplants.model.AddressModel;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository("AddressDaoImpl")
public class AddressDaoImpl {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<AddressModel> findAddressAll() {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM addess_view");
        return namedParameterJdbcTemplate.query(query.toString(), new AddressMapper());
    }

    public List<AddressModel> findAddressByKey(String key) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM addess_view WHERE address like ('%").append(key).append("%')");
        return namedParameterJdbcTemplate.query(query.toString(), new AddressMapper());
    }

}