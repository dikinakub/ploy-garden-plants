package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOdOrderListId(Long odOrderListId);
}
