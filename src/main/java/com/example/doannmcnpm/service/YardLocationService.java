package com.example.doannmcnpm.service;


import com.example.doannmcnpm.model.YardLocation;
import com.example.doannmcnpm.repository.YardLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YardLocationService {

    @Autowired
    YardLocationRepository yardLocationRepository;

    public void createYardLocation(YardLocation yardLocation) {
        yardLocationRepository.save(yardLocation);
    }
    public boolean findById(int yardLocationId)  {
        return yardLocationRepository.findById(yardLocationId).isPresent();
    }

    public  YardLocation findByID(int yardLocationId)   {
        return yardLocationRepository.findById(yardLocationId).get();
    }

    public void editYardLocation(int yardLocationId, YardLocation newYardLocation) {
        YardLocation yardLocation= yardLocationRepository.getById(yardLocationId);

        yardLocation.setName(newYardLocation.getName());
        yardLocation.setApartmentNumber(newYardLocation.getApartmentNumber());
        yardLocation.setStreetName(newYardLocation.getStreetName());
        yardLocation.setWardName(newYardLocation.getWardName());
        yardLocation.setDistrictName(newYardLocation.getDistrictName());
        yardLocation.setNote(newYardLocation.getNote());
        yardLocationRepository.save(yardLocation);
    }

    public List<YardLocation> getAllYardLocation() {
        return yardLocationRepository.findAll();
    }
}
