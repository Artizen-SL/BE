package com.example.artizen.repository;

import com.example.artizen.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByNickname(String nickname);

}
