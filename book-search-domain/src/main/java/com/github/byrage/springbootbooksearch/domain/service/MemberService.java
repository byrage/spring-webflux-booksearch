package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.exception.ExistsMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public boolean signUp(String memberId, String password) {

        if (memberRepository.findByMemberId(memberId)
                .isPresent()) {
            throw new ExistsMemberException("이미 존재하는 id 입니다.");
        }

        try {
            memberRepository.save(
                    Member.builder()
                            .memberId(memberId)
                            .password(password)
                            .build());
            return true;
        } catch (Exception e) {
            log.error("회원가입 에러 발생. memberId={}, password={}", memberId, password, e);
            return false;
        }
    }

    @Transactional
    public boolean signIn(String memberId, String password) {
        return memberRepository.findByMemberId(memberId)
                .map(member -> member.isMatchedPassword(password))
                .orElse(false);
    }
}
