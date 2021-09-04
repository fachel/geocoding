package com.belov.geocoding.geocoding.repository;

import com.belov.geocoding.geocoding.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepo extends JpaRepository<Request, Long> {
}
