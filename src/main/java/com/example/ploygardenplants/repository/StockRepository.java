package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.Stock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByStockTypeAndStockIsActiveOrderByStockName(String stockType, String stockIsActive);

    @Query("SELECT u FROM Stock u WHERE upper(u.stockName) like %:name% AND u.stockType = 'TREE' AND u.stockIsActive = 'Y' ORDER BY u.stockName")
    List<Stock> findByStockNameLike(@Param("name") String name);

    List<Stock> findByStockNameAndStockIsActive(String stockName, String stockIsActive);
    
    List<Stock> findByStockIdIn(List<Long> stockIds);
}
