package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.entity.CustomerEntity;
import com.example.ploygardenplants.repository.CustomerRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("api/save")
    public CustomerEntity save() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCusProfileName("Ae Peerayut");
        customerEntity.setCusUrlProfile("https://www.facebook.com/neon253500");
        customerEntity.setCusName("พีระยุทธ ถึงหมื่นไวย");
        customerEntity.setCusAddress("59/3 หมู่6 ซอยรวมพลัง ถนนศรีเพชร ต.หมื่นไวย อ.เมือง จ.นครราชสีมา 30000");
        customerEntity.setCusPhoneNumber("0934949935");
        customerEntity.setCusIsActive("Y");
        customerEntity.setCusCreateBy("diki");
        customerEntity.setCusCreateDatetime(new Date());
        return customerRepository.save(customerEntity);
    }

    @GetMapping("api/findAll")
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }
}
