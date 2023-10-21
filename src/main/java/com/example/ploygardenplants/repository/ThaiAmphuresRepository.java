package com.example.ploygardenplants.repository;

import com.example.ploygardenplants.entity.ThaiAmphures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiAmphuresRepository extends JpaRepository<ThaiAmphures, Long> {
    
}
