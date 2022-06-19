package com.example.doannmcnpm.controller;

import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.dto.SoccerFieldDto;
import com.example.doannmcnpm.model.Category;
import com.example.doannmcnpm.model.SoccerField;
import com.example.doannmcnpm.model.YardLocation;
import com.example.doannmcnpm.repository.CategoryRepository;
import com.example.doannmcnpm.repository.YardLocationRepository;
import com.example.doannmcnpm.service.SoccerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/soccerField")
@CrossOrigin
public class SoccerFieldController {

    @Autowired
    SoccerFieldService soccerFieldService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    YardLocationRepository yardLocationRepository;

    private final ValidatorFactory valFac= Validation.buildDefaultValidatorFactory();


    @GetMapping("/list")
    public List<SoccerField> listSoccerField()  {
        return soccerFieldService.listSoccerField();
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSoccerField(@RequestBody SoccerFieldDto soccerFieldDto)  {
        Optional<Category> optionalCategory= categoryRepository.findById(soccerFieldDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exists"), HttpStatus.NOT_FOUND);
        }

        Optional<YardLocation> optionalYardLocation= yardLocationRepository.findById(soccerFieldDto.getYardLocationId());
        if(!optionalYardLocation.isPresent())   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "YardLocation does not exists"), HttpStatus.NOT_FOUND);
        }


        soccerFieldService.createSoccerField(soccerFieldDto, optionalCategory.get(), optionalYardLocation.get());
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "SoccerField has been added"), HttpStatus.CREATED);
    }


    @CrossOrigin
    @PutMapping("/update/{soccerFieldId}")
    public ResponseEntity<ApiResponse> updateSoccerField(@PathVariable("soccerFieldId") int soccerFieldId, @RequestBody SoccerFieldDto soccerFieldDto ) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(soccerFieldDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exists"), HttpStatus.BAD_REQUEST);
        }
        Optional<YardLocation> optionalYardLocation= yardLocationRepository.findById(soccerFieldDto.getYardLocationId());
        if(!optionalYardLocation.isPresent())   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "YardLocation does not exists"), HttpStatus.NOT_FOUND);
        }

        soccerFieldService.updateSoccerField(soccerFieldDto, soccerFieldId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "SoccerField has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("delete/{soccerFieldId}")
    public ResponseEntity<ApiResponse> deleteSoccerField(@RequestParam int soccerFieldId)    {
        if(!soccerFieldService.findById(soccerFieldId)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "SoccerField does not exits"), HttpStatus.BAD_REQUEST);
        }

        soccerFieldService.deleteSoccerField(soccerFieldId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "SoccerField has been deleted"), HttpStatus.OK);


    }
}
