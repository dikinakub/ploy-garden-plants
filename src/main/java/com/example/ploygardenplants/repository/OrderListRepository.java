package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

    @Query(value = "select\n"
            + "	distinct ol.ol_reference_no\n"
            + "from\n"
            + "	public.order_list ol\n"
            + "where\n"
            + "	ol.ol_create_datetime > DATE(:currentDateISO8601)\n"
            + "order by\n"
            + "	ol.ol_reference_no desc\n"
            + "limit 1",
            nativeQuery = true)
    String findLastestByCurrentDateISO8601(@Param(value = "currentDateISO8601") String currentDateISO8601);

    @Query(value = "SELECT nextval('order_running_no_seq')",
            nativeQuery = true)
    Long nextVal();

    @Query(value = "SELECT setval('order_running_no_seq', 0)",
            nativeQuery = true)
    Long resetRunningNo();
}
