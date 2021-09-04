package com.belov.geocoding.geocoding.service;

import com.belov.geocoding.geocoding.entity.Request;
import com.belov.geocoding.geocoding.repository.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Override
    public void save(Request request) {
        requestRepo.save(request);
    }
}
