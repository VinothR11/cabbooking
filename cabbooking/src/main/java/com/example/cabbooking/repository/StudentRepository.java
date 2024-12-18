package com.example.cabbooking.repository;

import com.example.cabbooking.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Cab,Long> {
}