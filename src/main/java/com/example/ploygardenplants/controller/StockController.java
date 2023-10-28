package com.example.ploygardenplants.controller;

import com.example.ploygardenplants.entity.Stock;
import com.example.ploygardenplants.repository.StockRepository;
import com.example.ploygardenplants.request.StockRequest;
import com.example.ploygardenplants.response.SearchStockResponse;
import com.example.ploygardenplants.util.NumberUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/stock/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveStock(@RequestBody StockRequest request) {
        List<Stock> findByStockName = stockRepository.findByStockNameAndStockIsActive(request.getName(), "Y");
        if (!findByStockName.isEmpty()) {
            return new ResponseEntity<>("Name is duplicate. (ซ้ำ)", HttpStatus.BAD_REQUEST);
        }

        Stock stock = new Stock();
        stock.setStockName(request.getName());
        stock.setStockPurchasePrice(request.getPurchasePrice());
        stock.setStockSellingPrice(request.getSellingPrice());
        stock.setStockRemaining(request.getRemaining());
        stock.setStockType(request.getType());
        stock.setStockIsActive("Y");
        stock.setStockCreateBy("SYSTEM");
        stock.setStockCreateDatetime(new Date());
        stockRepository.save(stock);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/stock/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStock(@RequestBody StockRequest request) {
        Optional<Stock> findById = stockRepository.findById(request.getStockId());
        if (findById.isPresent()) {
            Stock stock = findById.get();
            stock.setStockIsActive("N");
            stock.setStockUpdateBy("SYSTEM");
            stock.setStockUpdateDatetime(new Date());
            stockRepository.save(stock);
        }

        Stock stock = new Stock();
        stock.setStockName(request.getName());
        stock.setStockPurchasePrice(request.getPurchasePrice());
        stock.setStockSellingPrice(request.getSellingPrice());
        stock.setStockRemaining(request.getRemaining());
        stock.setStockType(request.getType());
        stock.setStockIsActive("Y");
        stock.setStockCreateBy("SYSTEM");
        stock.setStockCreateDatetime(new Date());
        stockRepository.save(stock);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "api/stock/delete/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable Long id) {
        Optional<Stock> findById = stockRepository.findById(id);
        if (findById.isPresent()) {
            Stock stock = findById.get();
            stockRepository.delete(stock);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "api/stock/findByType/{type}")
    public List<SearchStockResponse> findByType(@PathVariable String type) {
        List<Stock> findAll = stockRepository.findByStockTypeAndStockIsActiveOrderByStockName(type, "Y");
        return mapData(findAll);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "api/stock/findByName/{name}")
    public List<SearchStockResponse> findByName(@PathVariable String name) {
        List<Stock> findByStockNameLike = stockRepository.findByStockNameLike(name);
        return mapData(findByStockNameLike);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "api/stock/findById/{id}")
    public List<SearchStockResponse> findById(@PathVariable Long id) {
        Optional<Stock> findById = stockRepository.findById(id);
        List<Stock> collect = findById.stream().collect(Collectors.toList());
        return mapData(collect);
    }

    private List<SearchStockResponse> mapData(List<Stock> stocks) {
        List<SearchStockResponse> responseList = new ArrayList<>();
        int no = 0;
        for (Stock stock : stocks) {
            SearchStockResponse res = new SearchStockResponse();
            res.setNo(++no);
            res.setStockId(stock.getStockId());
            res.setStockName(stock.getStockName());
            res.setStockPurchasePrice(NumberUtils.formatCurrency(new BigDecimal(stock.getStockPurchasePrice())));
            res.setStockSellingPrice(NumberUtils.formatCurrency(new BigDecimal(stock.getStockSellingPrice())));
            res.setStockRemaining(NumberUtils.formatInteger(stock.getStockRemaining()));
            res.setStockType(stock.getStockType());
            responseList.add(res);
        }
        return responseList;
    }

}
