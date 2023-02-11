package com.example.artizen.repository;

import com.example.artizen.entity.Myticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyticketRepository extends JpaRepository<Myticket, Long> {
}
