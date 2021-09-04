package com.belov.geocoding.geocoding.repository;

import com.belov.geocoding.geocoding.entity.Exception_;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepo extends JpaRepository<Exception_, Long> {
}
