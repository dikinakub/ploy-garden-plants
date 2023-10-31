package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.OrderPackaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPackagingRepository extends JpaRepository<OrderPackaging, Long> {

}
