package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.CustomerAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    
    List<CustomerAddress> findByAddCusIdAndAddIsActive(Long addCusId, String addIsActive);

    List<CustomerAddress> findByAddCusId(Long addCusId);
    
    List<CustomerAddress> findByAddIdAndAddIsActive(Long addId, String addIsActive);

}
