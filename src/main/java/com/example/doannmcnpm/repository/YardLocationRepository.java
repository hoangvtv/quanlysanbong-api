package com.example.doannmcnpm.repository;

import com.example.doannmcnpm.model.YardLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YardLocationRepository extends JpaRepository<YardLocation,Integer > {

}
