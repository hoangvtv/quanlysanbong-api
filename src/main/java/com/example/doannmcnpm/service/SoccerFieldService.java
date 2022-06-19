package com.example.doannmcnpm.service;


import com.example.doannmcnpm.dto.SoccerFieldDto;
import com.example.doannmcnpm.model.Category;
import com.example.doannmcnpm.model.SoccerField;
import com.example.doannmcnpm.model.YardLocation;
import com.example.doannmcnpm.repository.CategoryRepository;
import com.example.doannmcnpm.repository.SoccerFieldRepository;
import com.example.doannmcnpm.repository.YardLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SoccerFieldService {

    @Autowired
    SoccerFieldRepository soccerFieldRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    YardLocationRepository yardLocationRepository;

    public List<SoccerField> listSoccerField() {
        return soccerFieldRepository.findAll();
    }

    public void createSoccerField(SoccerFieldDto soccerFieldDto, Category category, YardLocation yardLocation) {
        SoccerField soccerField=new SoccerField();
        soccerField.setName(soccerFieldDto.getName());
        soccerField.setDescription(soccerFieldDto.getDescription());
        soccerField.setPrice(soccerFieldDto.getPrice());
        soccerField.setStatus(soccerFieldDto.getStatus());
        soccerField.setCategory(category);
        soccerField.setYardLocation(yardLocation);
        soccerFieldRepository.save(soccerField);
    }

    public void updateSoccerField(SoccerFieldDto soccerFieldDto, int soccerFieldId) throws Exception {
        Optional<SoccerField> optionalSoccerField=soccerFieldRepository.findById(soccerFieldId);
        if(!optionalSoccerField.isPresent())    {
            throw new Exception("SoccerField is not present");
        }
        SoccerField soccerField=optionalSoccerField.get();
        soccerField.setName(soccerFieldDto.getName());
        soccerField.setDescription(soccerFieldDto.getDescription());
        soccerField.setStatus(soccerFieldDto.getStatus());
        soccerField.setPrice(soccerFieldDto.getPrice());
        Category category=categoryRepository.findById(soccerFieldDto.getCategoryId()).get();
        soccerField.setCategory(category);
        YardLocation yardLocation=yardLocationRepository.findById(soccerFieldDto.getYardLocationId()).get();
        soccerField.setYardLocation(yardLocation);
        soccerFieldRepository.save(soccerField);
    }

    public boolean findById(int soccerFieldId) {
        return soccerFieldRepository.findById(soccerFieldId).isPresent();
    }

    public  SoccerField findByID(int soccerFieldId) {
        return soccerFieldRepository.findById(soccerFieldId).get();
    }

    public void deleteSoccerField(int soccerFieldId) {
         soccerFieldRepository.deleteById(soccerFieldId);
    }

    public List<SoccerField> listSoccerFieldByCategoryAndYardlocation(Long caterogy_id, Long yardLocation_id)   {
        List<SoccerField>  list= new ArrayList<>();
        soccerFieldRepository.findAll().forEach(item -> {
            if(item.getCategory().getId()==caterogy_id && item.getYardLocation().getId()==yardLocation_id)  {

                list.add(item);
            }
        });

        return  list;
    }
}
