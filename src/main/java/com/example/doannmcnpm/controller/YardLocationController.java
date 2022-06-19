package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.model.YardLocation;
import com.example.doannmcnpm.service.YardLocationService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/yardLocation")
@CrossOrigin
public class YardLocationController {

    @Autowired
    YardLocationService yardLocationService;


    @GetMapping(value = "/list")
    public ResponseEntity<List<YardLocation>> getYardLocation() {
        List<YardLocation> yardLocationList = yardLocationService.getAllYardLocation();
        return new ResponseEntity<>(yardLocationList, HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse> createYardLocation(@RequestBody YardLocation yardLocation) {

        ValidatorFactory valFac = Validation.buildDefaultValidatorFactory();
        Validator val = valFac.getValidator();
        Set<ConstraintViolation<YardLocation>> validations = val.validate(yardLocation);
        for (ConstraintViolation<YardLocation> validation : validations) {
            System.out.println(validation.getMessage());
        }

        if (validations.size() == 0) {
            yardLocationService.createYardLocation(yardLocation);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "YardLocation has been added"), HttpStatus.CREATED);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "YardLocation is not created"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/edit/{yardLocationId}")
    public ResponseEntity<ApiResponse> editYardLocation(@PathVariable("yardLocationId") int yardLocationId , @RequestBody String yardLocation)   {

        Gson gson = new Gson();
        YardLocation newYardLocation= gson.fromJson(yardLocation, YardLocation.class);
        if (!yardLocationService.findById(yardLocationId)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "YardLocation does not exists"), HttpStatus.NOT_FOUND);
        }

        yardLocationService.editYardLocation(yardLocationId,newYardLocation);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "YardLocation has been updated"), HttpStatus.OK);
    }
}
