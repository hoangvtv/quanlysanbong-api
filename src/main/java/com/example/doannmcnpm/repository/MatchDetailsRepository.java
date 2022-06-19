package com.example.doannmcnpm.repository;


import com.example.doannmcnpm.model.MatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDetailsRepository extends JpaRepository<MatchDetails, Integer> {
}
