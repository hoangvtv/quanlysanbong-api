package com.example.doannmcnpm.repository;

import com.example.doannmcnpm.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
}
