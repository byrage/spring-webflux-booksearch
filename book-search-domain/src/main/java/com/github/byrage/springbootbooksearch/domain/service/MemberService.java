package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.exception.ExistsMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signUp(String memberId, String password) {

        if (memberRepository.findByMemberId(memberId)
                .isPresent()) {
            throw new ExistsMemberException("이미 존재하는 id 입니다.");
        }

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
