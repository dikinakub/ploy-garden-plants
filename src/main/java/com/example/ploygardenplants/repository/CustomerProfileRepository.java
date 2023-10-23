package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.CustomerProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

    @Query("SELECT u FROM CustomerProfile u ORDER BY cusCreateDatetime DESC")
    List<CustomerProfile> findAllOrderByCusCreateDatetimeDesc();

    @Query("SELECT u FROM CustomerProfile u WHERE upper(u.cusProfileName) like %:cusProfileName% ORDER BY cusCreateDatetime DESC")
    List<CustomerProfile> findByCusProfileName(@Param("cusProfileName") String cusProfileName);

}
