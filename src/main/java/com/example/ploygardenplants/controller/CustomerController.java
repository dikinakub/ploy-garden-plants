package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.dao.AddressDaoImpl;
import com.example.ploygardenplants.entity.CustomerAddress;
import com.example.ploygardenplants.entity.CustomerProfile;
import com.example.ploygardenplants.entity.ThaiAmphures;
import com.example.ploygardenplants.entity.ThaiProvinces;
import com.example.ploygardenplants.entity.ThaiTambons;
import com.example.ploygardenplants.model.AddressDetailModel;
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
        customerAddress.setAddTambonsId(customerRequest.getTambonsId());
        customerAddress.setAddPhoneNumber1(customerRequest.getPhoneNumber1());
        customerAddress.setAddPhoneNumber2(customerRequest.getPhoneNumber2().isEmpty() ? null : customerRequest.getPhoneNumber2());
        customerAddress.setDefaultFlag(Boolean.TRUE);
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

        Optional<CustomerAddress> customerAddress = customerAddressRepository.findById(updateCustomerRequest.getProfileID());
        if (customerAddress.isPresent()) {
            CustomerAddress address = customerAddress.get();
            address.setAddName(updateCustomerRequest.getAddressName());
            address.setAddAddressDetail(updateCustomerRequest.getAddressDetail());
            address.setAddTambonsId(updateCustomerRequest.getTambonsId());
            address.setAddPhoneNumber1(updateCustomerRequest.getPhoneNumber1());
            address.setAddPhoneNumber2(updateCustomerRequest.getPhoneNumber2() == null || updateCustomerRequest.getPhoneNumber2().isEmpty() ? null : updateCustomerRequest.getPhoneNumber2());
            address.setAddUpdateBy("SYSTEM");
            address.setAddUpdateDatetime(new Date());
            customerAddressRepository.save(address);
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
            } else {
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
        List<CustomerAddress> findById = customerAddressRepository.findByAddIdAndAddIsActive(id, "Y");
        if (!findById.isEmpty()) {
            CustomerAddress customerAddress = findById.get(0);
            Optional<CustomerProfile> customerProfile = customerRepository.findById(customerAddress.getAddCusId());
            if (customerAddress.getAddTambonsId() != null) {
                ThaiTambons thaiTambons = thaiTambonsRepository.findByTambonId(customerAddress.getAddTambonsId());
                Optional<ThaiAmphures> thaiAmphures = thaiAmphuresRepository.findById(thaiTambons.getAmphureId());

                searchRes.setProfileID(customerAddress.getAddId());
                searchRes.setProfileName(customerProfile.get().getCusProfileName());
                searchRes.setProfileUrl(customerProfile.get().getCusProfileUrl());
                searchRes.setAddressName(customerAddress.getAddName());
                searchRes.setAddressDetail(customerAddress.getAddAddressDetail());
                searchRes.setProvincesId(thaiAmphures.get().getProvinceId());
                searchRes.setAmphuresId(thaiAmphures.get().getId());
                searchRes.setTambonsId(thaiTambons.getTambonId());
                searchRes.setZipCode(thaiTambons.getZipCode());
                searchRes.setPhoneNumber1(customerAddress.getAddPhoneNumber1());
                searchRes.setPhoneNumber2(customerAddress.getAddPhoneNumber2());
                return searchRes;
            } else {
                searchRes.setProfileID(customerAddress.getAddId());
                searchRes.setProfileName(customerProfile.get().getCusProfileName());
                searchRes.setProfileUrl(customerProfile.get().getCusProfileUrl());
                return searchRes;
            }
        }
        return null;
    }

    private List<SearchCustomerProfileResponse> mapData(List<CustomerProfile> customerProfileList) {
        List<SearchCustomerProfileResponse> responseList = new ArrayList<>();
        int no = 0;
        for (CustomerProfile customerProfile : customerProfileList) {
            SearchCustomerProfileResponse response = new SearchCustomerProfileResponse();
            List<AddressDetailModel> addDetailList = new ArrayList<>();
            response.setNo(++no);
            response.setProfileID(customerProfile.getCusId());
            response.setProfileName(customerProfile.getCusProfileName());
            response.setProfileUrl(customerProfile.getCusProfileUrl());

            List<CustomerAddress> customerAddressList = customerAddressRepository.findByAddCusIdAndAddIsActive(customerProfile.getCusId(), "Y");
            if (customerAddressList != null) {
                for (CustomerAddress customerAddress : customerAddressList) {
                    if (customerAddress.getAddTambonsId() != null) {
                        ThaiTambons thaiTambons = thaiTambonsRepository.findByTambonId(customerAddress.getAddTambonsId());
                        Optional<ThaiAmphures> thaiAmphures = thaiAmphuresRepository.findById(thaiTambons.getAmphureId());
                        Optional<ThaiProvinces> thaiProvinces = thaiProvincesRepository.findById(thaiAmphures.get().getProvinceId());
                        String address = "";
                        if (thaiProvinces.get().getId().equals(1L)) {
                            address = customerAddress.getAddAddressDetail().trim()
                                    + " แขวง" + thaiTambons.getNameTh()
                                    + " " + thaiAmphures.get().getNameTh()
                                    + " จังหวัด" + thaiProvinces.get().getNameTh()
                                    + " " + thaiTambons.getZipCode();
                        } else {
                            address = customerAddress.getAddAddressDetail().trim()
                                    + " ตำบล" + thaiTambons.getNameTh()
                                    + " อำเภอ" + thaiAmphures.get().getNameTh()
                                    + " จังหวัด" + thaiProvinces.get().getNameTh()
                                    + " " + thaiTambons.getZipCode();
                        }

                        AddressDetailModel addressDetail = new AddressDetailModel();
                        addressDetail.setAddressId(customerAddress.getAddId());
                        addressDetail.setAddressName(customerAddress.getAddName());
                        addressDetail.setAddressDetail(address);
                        String p1 = customerAddress.getAddPhoneNumber1().substring(0, 2);
                        String p2 = customerAddress.getAddPhoneNumber1().substring(2, 6);
                        String p3 = customerAddress.getAddPhoneNumber1().substring(6, 10);
                        addressDetail.setPhoneNumber1(p1 + "-" + p2 + "-" + p3);
                        if (customerAddress.getAddPhoneNumber2() != null && !customerAddress.getAddPhoneNumber1().isEmpty()) {
                            String ph1 = customerAddress.getAddPhoneNumber2().substring(0, 2);
                            String ph2 = customerAddress.getAddPhoneNumber2().substring(2, 6);
                            String ph3 = customerAddress.getAddPhoneNumber2().substring(6, 10);
                            addressDetail.setPhoneNumber2(ph1 + "-" + ph2 + "-" + ph3);
                        }
                        addressDetail.setDefaultFlag(customerAddress.getDefaultFlag());
                        addDetailList.add(addressDetail);
                    } else {
                        AddressDetailModel addressDetail = new AddressDetailModel();
                        addressDetail.setAddressId(customerAddress.getAddId());
                        addressDetail.setAddressName(customerAddress.getAddName());
                        addressDetail.setDefaultFlag(customerAddress.getDefaultFlag());
                        addDetailList.add(addressDetail);
                    }
                }
            }
            response.setAddressDetailList(addDetailList);
            responseList.add(response);
        }
        return responseList;
    }

    @GetMapping("api/customer/checkCustomerByName/{name}")
    public List<SearchCustomerProfileResponse> checkCustomer(@PathVariable String name) {
        List<CustomerProfile> findByCusProfileName = customerRepository.findByCusProfileNameLike(name.toUpperCase());
        if (findByCusProfileName == null || findByCusProfileName.isEmpty()) {
            return null;
        }
        return mapCheckCustomer(findByCusProfileName);
    }

    public List<SearchCustomerProfileResponse> mapCheckCustomer(List<CustomerProfile> findByCusProfileName) {
        List<SearchCustomerProfileResponse> responseList = new ArrayList<>();
        int no = 0;
        for (CustomerProfile customerProfile : findByCusProfileName) {
            SearchCustomerProfileResponse response = new SearchCustomerProfileResponse();
            List<AddressDetailModel> addDetailList = new ArrayList<>();
            response.setNo(++no);
            response.setProfileID(customerProfile.getCusId());
            response.setProfileName(customerProfile.getCusProfileName());
            response.setProfileUrl(customerProfile.getCusProfileUrl());

            List<CustomerAddress> customerAddressList = customerAddressRepository.findByAddCusIdAndAddIsActive(customerProfile.getCusId(), "Y");
            if (customerAddressList != null) {
                for (CustomerAddress customerAddress : customerAddressList) {
                    if (customerAddress.getDefaultFlag()) {
                        ThaiTambons thaiTambons = thaiTambonsRepository.findByTambonId(customerAddress.getAddTambonsId());
                        Optional<ThaiAmphures> thaiAmphures = thaiAmphuresRepository.findById(thaiTambons.getAmphureId());
                        Optional<ThaiProvinces> thaiProvinces = thaiProvincesRepository.findById(thaiAmphures.get().getProvinceId());
                        String address = "";
                        if (thaiProvinces.get().getId().equals(1L)) {
                            address = customerAddress.getAddAddressDetail().trim()
                                    + " แขวง" + thaiTambons.getNameTh()
                                    + " " + thaiAmphures.get().getNameTh()
                                    + " จังหวัด" + thaiProvinces.get().getNameTh()
                                    + " " + thaiTambons.getZipCode();
                        } else {
                            address = customerAddress.getAddAddressDetail().trim()
                                    + " ตำบล" + thaiTambons.getNameTh()
                                    + " อำเภอ" + thaiAmphures.get().getNameTh()
                                    + " จังหวัด" + thaiProvinces.get().getNameTh()
                                    + " " + thaiTambons.getZipCode();
                        }

                        AddressDetailModel addressDetail = new AddressDetailModel();
                        addressDetail.setAddressId(customerAddress.getAddId());
                        addressDetail.setAddressName(customerAddress.getAddName());
                        addressDetail.setAddressDetail(address);
                        String p1 = customerAddress.getAddPhoneNumber1().substring(0, 2);
                        String p2 = customerAddress.getAddPhoneNumber1().substring(2, 6);
                        String p3 = customerAddress.getAddPhoneNumber1().substring(6, 10);
                        addressDetail.setPhoneNumber1(p1 + "-" + p2 + "-" + p3);
                        if (customerAddress.getAddPhoneNumber2() != null && !customerAddress.getAddPhoneNumber1().isEmpty()) {
                            String ph1 = customerAddress.getAddPhoneNumber2().substring(0, 2);
                            String ph2 = customerAddress.getAddPhoneNumber2().substring(2, 6);
                            String ph3 = customerAddress.getAddPhoneNumber2().substring(6, 10);
                            addressDetail.setPhoneNumber2(ph1 + "-" + ph2 + "-" + ph3);
                        }
                        addressDetail.setDefaultFlag(customerAddress.getDefaultFlag());
                        addDetailList.add(addressDetail);
                    }
                }
            }
            response.setAddressDetailList(addDetailList);
            responseList.add(response);
        }
        return responseList;
    }

    @GetMapping("api/customer/findProvincesAll")
    public List<ThaiProvinces> findProvincesAll() {
        return thaiProvincesRepository.findAllByOrderByNameThAsc();
    }

    @GetMapping("api/customer/findProvinces/{key}")
    public List<ThaiProvinces> findProvinces(@PathVariable String key) {
        return thaiProvincesRepository.findByNameThStartsWith(key);
    }

    @GetMapping("api/customer/findAmphuresByProvincesId/{provincesId}")
    public List<ThaiAmphures> findAmphuresByProvincesId(@PathVariable Long provincesId) {
        return thaiAmphuresRepository.findByProvinceIdOrderByNameThAsc(provincesId);
    }

    @GetMapping("api/customer/findAmphures/{provincesId}/{key}")
    public List<ThaiAmphures> findAmphures(@PathVariable Long provincesId, @PathVariable String key) {
        return thaiAmphuresRepository.findByNameThContainingAndProvinceId(key, provincesId);
    }

    @GetMapping("api/customer/findTambonsByAmphureId/{amphureId}")
    public List<ThaiTambons> findTambonsByAmphureId(@PathVariable Long amphureId) {
        return thaiTambonsRepository.findByAmphureIdOrderByNameThAsc(amphureId);
    }

    @GetMapping("api/customer/findTambons/{amphureId}/{key}")
    public List<ThaiTambons> findTambons(@PathVariable Long amphureId, @PathVariable String key) {
        return thaiTambonsRepository.findByNameThStartsWithAndAmphureId(key, amphureId);
    }

    @GetMapping("api/customer/findTambonsById/{id}")
    public ThaiTambons findTambonsById(@PathVariable Long id) {
        Optional<ThaiTambons> findById = thaiTambonsRepository.findById(id);
        return findById.get();
    }

    @PostMapping(value = "api/customer/updateCustomerDefaultFlag/{id}")
    public ResponseEntity<String> updateCustomerDefaultFlag(@PathVariable Long id) {
        Optional<CustomerAddress> findById = customerAddressRepository.findById(id);
        List<CustomerAddress> forSave = new ArrayList<>();
        if (findById.isPresent()) {
            List<CustomerAddress> findByAddCusId = customerAddressRepository.findByAddCusId(findById.get().getAddCusId());
            findByAddCusId.forEach(a -> a.setDefaultFlag(Boolean.FALSE));
            forSave.addAll(findByAddCusId);

            findById.get().setDefaultFlag(Boolean.TRUE);
            forSave.add(findById.get());
        }
        if (!forSave.isEmpty()) {
            customerAddressRepository.saveAll(forSave);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("api/customer/findAddressAll")
    public List<AddressModel> findAddressAll() {
        return addressDaoImpl.findAddressAll();
    }
}
