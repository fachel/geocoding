package com.belov.geocoding.geocoding.service;

import com.belov.geocoding.geocoding.entity.Exception_;
import com.belov.geocoding.geocoding.repository.ExceptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Autowired
    private ExceptionRepo exceptionRepo;

    @Override
    public void save(Exception_ exception) {
        exceptionRepo.save(exception);
    }
}
