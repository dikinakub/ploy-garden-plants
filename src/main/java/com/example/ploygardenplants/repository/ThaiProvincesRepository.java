package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.ThaiProvinces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiProvincesRepository extends JpaRepository<ThaiProvinces, Long> {

    List<ThaiProvinces> findAllByOrderByNameThAsc();

    List<ThaiProvinces> findByNameThStartsWith(String nameTh);
}
