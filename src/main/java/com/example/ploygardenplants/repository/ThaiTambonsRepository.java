package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.ThaiTambons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiTambonsRepository extends JpaRepository<ThaiTambons, Long> {

    ThaiTambons findByTambonId(Long tambonId);
}
