package com.example.artizen.repository;

import com.example.artizen.entity.Myticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyticketRepository extends JpaRepository<Myticket, Long> {

    Slice<Myticket> findByMember_Id(String memberId, Pageable pageable);
}
