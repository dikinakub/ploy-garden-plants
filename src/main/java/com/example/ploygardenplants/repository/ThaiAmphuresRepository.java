package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.ThaiAmphures;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiAmphuresRepository extends JpaRepository<ThaiAmphures, Long> {

    List<ThaiAmphures> findByProvinceIdOrderByNameThAsc(Long provinceId);

    List<ThaiAmphures> findByNameThContainingAndProvinceId(String nameTh, Long provinceId);
}
