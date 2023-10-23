package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.ThaiAmphures;
import com.example.ploygardenplants.entity.ThaiProvinces;
import com.example.ploygardenplants.entity.ThaiTambons;
import com.example.ploygardenplants.repository.CustomerAddressRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ploygardenplants.repository.CustomerProfileRepository;
import com.example.ploygardenplants.repository.ThaiAmphuresRepository;
import com.example.ploygardenplants.repository.ThaiProvincesRepository;
import com.example.ploygardenplants.repository.ThaiTambonsRepository;
import com.example.ploygardenplants.response.SearchCustomerProfileResponse;
import java.util.ArrayList;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerProfileRepository customerRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private ThaiTambonsRepository thaiTambonsRepository;

    @Autowired
    private ThaiAmphuresRepository thaiAmphuresRepository;

    @Autowired
    private ThaiProvincesRepository thaiProvincesRepository;

    @PostMapping("api/customer/save")
    public CustomerProfile save() {
        CustomerProfile customerEntity = new CustomerProfile();
//        customerEntity.setCusProfileName("Diki Kawasaki");
//        customerEntity.setCusUrlProfile("https://www.facebook.com/diki.kawasaki/");
//        customerEntity.setCusName("Diki Kawasaki");
//        customerEntity.setCusAddress("180 ม.5");
//        customerEntity.setCusTambonsId(601304l);
//        customerEntity.setCusPhoneNumber("0623540001");
//        customerEntity.setCusIsActive("Y");
//        customerEntity.setCusCreateBy("diki");
//        customerEntity.setCusCreateDatetime(new Date());
        return customerRepository.save(customerEntity);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/customer/findAll")
    public List<SearchCustomerProfileResponse> findAll() {
        List<SearchCustomerProfileResponse> responseList = new ArrayList<>();
        List<CustomerProfile> findAllOrderByCusCreateDatetimeDesc = customerRepository.findAllOrderByCusCreateDatetimeDesc();
        int no = 0;
        for (CustomerProfile customerProfile : findAllOrderByCusCreateDatetimeDesc) {
            List<CustomerAddress> customerAddress = customerAddressRepository.findByAddCusIdAndAddIsActive(customerProfile.getCusId(), "Y");
            ThaiTambons thaiTambons = thaiTambonsRepository.findByTambonId(customerAddress.get(0).getAddTambonsId());
            Optional<ThaiAmphures> thaiAmphures = thaiAmphuresRepository.findById(thaiTambons.getAmphureId());
            Optional<ThaiProvinces> thaiProvinces = thaiProvincesRepository.findById(thaiAmphures.get().getProvinceId());
            String address = "";
            if (thaiProvinces.get().getId().equals(1L)) {
                address = customerAddress.get(0).getAddAddressDetail().trim()
                        + " แขวง" + thaiTambons.getNameTh()
                        + " " + thaiAmphures.get().getNameTh()
                        + " จังหวัด" + thaiProvinces.get().getNameTh()
                        + " " + thaiTambons.getZipCode();
            } else {
                address = customerAddress.get(0).getAddAddressDetail().trim()
                        + " ตำบล" + thaiTambons.getNameTh()
                        + " อำเภอ" + thaiAmphures.get().getNameTh()
                        + " จังหวัด" + thaiProvinces.get().getNameTh()
                        + " " + thaiTambons.getZipCode();
            }

            SearchCustomerProfileResponse response = new SearchCustomerProfileResponse();
            response.setNo(++no);
            response.setProfileName(customerProfile.getCusProfileName());
            response.setProfileUrl(customerProfile.getCusProfileUrl());
            response.setAddressName(customerAddress.get(0).getAddName());
            response.setAddressDetail(address);
            String p1 = customerAddress.get(0).getAddPhoneNumber1().substring(0, 2);
            String p2 = customerAddress.get(0).getAddPhoneNumber1().substring(2, 6);
            String p3 = customerAddress.get(0).getAddPhoneNumber1().substring(6, 10);
            response.setPhoneNumber1(p1 + "-" + p2 + "-" + p3);
            if (customerAddress.get(0).getAddPhoneNumber2() != null && !customerAddress.get(0).getAddPhoneNumber1().isEmpty()) {
                String ph1 = customerAddress.get(0).getAddPhoneNumber2().substring(0, 2);
                String ph2 = customerAddress.get(0).getAddPhoneNumber2().substring(2, 6);
                String ph3 = customerAddress.get(0).getAddPhoneNumber2().substring(6, 10);
                response.setPhoneNumber2(ph1 + "-" + ph2 + "-" + ph3);
            }
            responseList.add(response);
        }
        return responseList;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("api/customer/findByName/{name}")
    public List<SearchCustomerProfileResponse> findByName(@PathVariable String name) {
        List<SearchCustomerProfileResponse> responseList = new ArrayList<>();
        List<CustomerProfile> findAllOrderByCusCreateDatetimeDesc = customerRepository.findByCusProfileName(name);
        int no = 0;
        for (CustomerProfile customerProfile : findAllOrderByCusCreateDatetimeDesc) {
            List<CustomerAddress> customerAddress = customerAddressRepository.findByAddCusIdAndAddIsActive(customerProfile.getCusId(), "Y");
            ThaiTambons thaiTambons = thaiTambonsRepository.findByTambonId(customerAddress.get(0).getAddTambonsId());
            Optional<ThaiAmphures> thaiAmphures = thaiAmphuresRepository.findById(thaiTambons.getAmphureId());
            Optional<ThaiProvinces> thaiProvinces = thaiProvincesRepository.findById(thaiAmphures.get().getProvinceId());
            String address = "";
            if (thaiProvinces.get().getId().equals(1L)) {
                address = customerAddress.get(0).getAddAddressDetail().trim()
                        + " แขวง" + thaiTambons.getNameTh()
                        + " " + thaiAmphures.get().getNameTh()
                        + " จังหวัด" + thaiProvinces.get().getNameTh()
                        + " " + thaiTambons.getZipCode();
            } else {
                address = customerAddress.get(0).getAddAddressDetail().trim()
                        + " ตำบล" + thaiTambons.getNameTh()
                        + " อำเภอ" + thaiAmphures.get().getNameTh()
                        + " จังหวัด" + thaiProvinces.get().getNameTh()
                        + " " + thaiTambons.getZipCode();
            }

            SearchCustomerProfileResponse response = new SearchCustomerProfileResponse();
            response.setNo(++no);
            response.setProfileName(customerProfile.getCusProfileName());
            response.setProfileUrl(customerProfile.getCusProfileUrl());
            response.setAddressName(customerAddress.get(0).getAddName());
            response.setAddressDetail(address);
            String p1 = customerAddress.get(0).getAddPhoneNumber1().substring(0, 2);
            String p2 = customerAddress.get(0).getAddPhoneNumber1().substring(2, 6);
            String p3 = customerAddress.get(0).getAddPhoneNumber1().substring(6, 10);
            response.setPhoneNumber1(p1 + "-" + p2 + "-" + p3);
            if (customerAddress.get(0).getAddPhoneNumber2() != null && !customerAddress.get(0).getAddPhoneNumber1().isEmpty()) {
                String ph1 = customerAddress.get(0).getAddPhoneNumber2().substring(0, 2);
                String ph2 = customerAddress.get(0).getAddPhoneNumber2().substring(2, 6);
                String ph3 = customerAddress.get(0).getAddPhoneNumber2().substring(6, 10);
                response.setPhoneNumber2(ph1 + "-" + ph2 + "-" + ph3);
            }
            responseList.add(response);
        }
        return responseList;
    }
}
