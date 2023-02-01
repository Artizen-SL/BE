package com.example.artizen.security;

import com.example.artizen.entity.Member;
import com.example.artizen.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl {

    private final MemberRepository memberRepository;

    public MemberDetailsImpl loadMemberByMembername(String nickname) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalArgumentException("Can't find" + nickname));

        return new MemberDetailsImpl(member);
    }
}
