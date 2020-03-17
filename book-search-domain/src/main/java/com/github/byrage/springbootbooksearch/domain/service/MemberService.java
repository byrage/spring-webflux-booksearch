package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(String memberId, String password) {
        return memberRepository.save(
                Member.builder()
                        .memberId(memberId)
                        .password(password)
                        .build())
                ;
    }

    public boolean signIn(String memberId, String password) {
        return memberRepository.findByMemberId(memberId)
                .map(member -> member.isMatchedPassword(password))
                .orElse(false);
    }
}
