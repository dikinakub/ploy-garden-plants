package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.dao.AddressDaoImpl;
import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.ThaiAmphures;
import com.example.ploygardenplants.entity.ThaiProvinces;
import com.example.ploygardenplants.entity.ThaiTambons;
import com.example.ploygardenplants.model.AddressModel;
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
import com.example.ploygardenplants.request.CustomerRequest;
import com.example.ploygardenplants.request.UpdateCustomerRequest;
import com.example.ploygardenplants.response.SearchCustomerProfileResponse;
import com.example.ploygardenplants.response.SearchEditCustomerProfileResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerController {

    public static final String NUMBER_PATTERN = "^[0-9]+$";

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

    @Autowired
    private AddressDaoImpl addressDaoImpl;

    @PostMapping(value = "api/customer/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        List<CustomerProfile> findByCusProfileName = customerRepository.findByCusProfileName(customerRequest.getFacebookName());
        if (!findByCusProfileName.isEmpty()) {
            return new ResponseEntity<>("Facebook name is duplicate. (ซ้ำ)", HttpStatus.BAD_REQUEST);
        }

        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        if (!pattern.matcher(customerRequest.getPhoneNumber1()).matches()) {
            return new ResponseEntity<>("เบอร์โทร 1 ไม่ใช่ตัวเลข", HttpStatus.BAD_REQUEST);
        } else {
            if (customerRequest.getPhoneNumber1().length() != 10) {
                return new ResponseEntity<>("เบอร์โทร 1 ไม่ถึง 10 ตัว", HttpStatus.BAD_REQUEST);
            }
        }
        if (customerRequest.getPhoneNumber2() != null && !customerRequest.getPhoneNumber2().isEmpty()) {
            if (!pattern.matcher(customerRequest.getPhoneNumber2()).matches()) {
                return new ResponseEntity<>("เบอร์โทร 2 ไม่ใช่ตัวเลข", HttpStatus.BAD_REQUEST);
            } else {
                if (customerRequest.getPhoneNumber1().length() != 10) {
                    return new ResponseEntity<>("เบอร์โทร 2 ไม่ถึง 10 ตัว", HttpStatus.BAD_REQUEST);
                }
            }
        }

        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setCusProfileName(customerRequest.getFacebookName());
        customerProfile.setCusProfileUrl(customerRequest.getFacebookUrl());
        customerProfile.setCusCreateBy("SYSTEM");
        customerProfile.setCusCreateDatetime(new Date());
        CustomerProfile save = customerRepository.save(customerProfile);
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setAddCusId(save.getCusId());
        customerAddress.setAddName(customerRequest.getName());
        customerAddress.setAddAddressDetail(customerRequest.getAddressDetail());
        customerAddress.setAddTambonsId(customerRequest.getAddress());
        customerAddress.setAddPhoneNumber1(customerRequest.getPhoneNumber1());
        customerAddress.setAddPhoneNumber2(customerRequest.getPhoneNumber2().isEmpty() ? null : customerRequest.getPhoneNumber2());
        customerAddress.setAddIsActive("Y");
        customerAddress.setAddCreateBy("SYSTEM");
        customerAddress.setAddCreateDatetime(new Date());
        customerAddressRepository.save(customerAddress);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "api/customer/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCustomer(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        if (!pattern.matcher(updateCustomerRequest.getPhoneNumber1()).matches()) {
            return new ResponseEntity<>("เบอร์โทร 1 ไม่ใช่ตัวเลข", HttpStatus.BAD_REQUEST);
        } else {
            if (updateCustomerRequest.getPhoneNumber1().length() != 10) {
                return new ResponseEntity<>("เบอร์โทร 1 ไม่ถึง 10 ตัว", HttpStatus.BAD_REQUEST);
            }
        }
        if (updateCustomerRequest.getPhoneNumber2() != null && !updateCustomerRequest.getPhoneNumber2().isEmpty()) {
            if (!pattern.matcher(updateCustomerRequest.getPhoneNumber2()).matches()) {
                return new ResponseEntity<>("เบอร์โทร 2 ไม่ใช่ตัวเลข", HttpStatus.BAD_REQUEST);
            } else {
                if (updateCustomerRequest.getPhoneNumber1().length() != 10) {
                    return new ResponseEntity<>("เบอร์โทร 2 ไม่ถึง 10 ตัว", HttpStatus.BAD_REQUEST);
                }
            }
        }

        Optional<CustomerProfile> findById = customerRepository.findById(updateCustomerRequest.getProfileID());
        if (findById.isPresent()) {
            List<CustomerAddress> customerAddress = customerAddressRepository.findByAddCusIdAndAddIsActive(findById.get().getCusId(), "Y");
            if (!customerAddress.isEmpty()) {
                CustomerAddress address = customerAddress.get(0);
                address.setAddTambonsId(updateCustomerRequest.getAddress());
                address.setAddPhoneNumber1(updateCustomerRequest.getPhoneNumber1());
                address.setAddPhoneNumber2(updateCustomerRequest.getPhoneNumber2() == null || updateCustomerRequest.getPhoneNumber2().isEmpty() ? null : updateCustomerRequest.getPhoneNumber2());
                address.setAddUpdateBy("SYSTEM");
                address.setAddUpdateDatetime(new Date());
                customerAddressRepository.save(address);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "api/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        Optional<CustomerProfile> findById = customerRepository.findById(id);
        if (findById.isPresent()) {
            List<CustomerAddress> findByAddCusId = customerAddressRepository.findByAddCusId(findById.get().getCusId());
            if (!findByAddCusId.isEmpty()) {
                for (CustomerAddress address : findByAddCusId) {
                    customerAddressRepository.delete(address);
                }
                customerRepository.delete(findById.get());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("api/customer/findAll")
    public List<SearchCustomerProfileResponse> findAll() {
        List<CustomerProfile> findAllOrderByCusCreateDatetimeDesc = customerRepository.findAllOrderByCusCreateDatetimeDesc();
        return mapData(findAllOrderByCusCreateDatetimeDesc);
    }

    @GetMapping("api/customer/findByName/{name}")
    public List<SearchCustomerProfileResponse> findByName(@PathVariable String name) {
        List<CustomerProfile> findByCusProfileName = customerRepository.findByCusProfileNameLike(name.toUpperCase());
        return mapData(findByCusProfileName);
    }

    @GetMapping("api/customer/findById/{id}")
    public SearchEditCustomerProfileResponse findByCustomerId(@PathVariable Long id) {
        SearchEditCustomerProfileResponse searchRes = new SearchEditCustomerProfileResponse();
        Optional<CustomerProfile> findById = customerRepository.findById(id);
        if (findById.isPresent()) {
            CustomerProfile cus = findById.get();
            List<CustomerAddress> customerAddress = customerAddressRepository.findByAddCusIdAndAddIsActive(cus.getCusId(), "Y");
            if (customerAddress != null && !customerAddress.isEmpty()) {
                searchRes.setProfileID(cus.getCusId());
                searchRes.setProfileName(cus.getCusProfileName());
                searchRes.setProfileUrl(cus.getCusProfileUrl());
                searchRes.setAddressName(customerAddress.get(0).getAddName());
                searchRes.setAddressDetail(customerAddress.get(0).getAddAddressDetail());
                searchRes.setAddress(customerAddress.get(0).getAddTambonsId());
                searchRes.setPhoneNumber1(customerAddress.get(0).getAddPhoneNumber1());
                searchRes.setPhoneNumber2(customerAddress.get(0).getAddPhoneNumber2());
                return searchRes;
            }
        }
        return null;
    }

    private List<SearchCustomerProfileResponse> mapData(List<CustomerProfile> customerProfileList) {
        List<SearchCustomerProfileResponse> responseList = new ArrayList<>();
        int no = 0;
        for (CustomerProfile customerProfile : customerProfileList) {
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
            response.setProfileID(customerProfile.getCusId());
            responseList.add(response);
        }
        return responseList;
    }

    @GetMapping("api/customer/findAddressAll")
    public List<AddressModel> findAddressAll() {
        log.info("findAddressAll");
        return addressDaoImpl.findAddressAll();
    }

    @GetMapping("api/customer/findAddress/{key}")
    public List<AddressModel> findAddress(@PathVariable String key) {
        return addressDaoImpl.findAddressByKey(key);
    }
}
