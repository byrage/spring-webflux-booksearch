package com.github.byrage.springbootbooksearch.domain.service;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import com.github.byrage.springbootbooksearch.domain.exception.ExistsMemberException;
import com.github.byrage.springbootbooksearch.domain.repository.MemberRepository;
import com.github.byrage.springbootbooksearch.domain.util.PasswordUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void signUp() {
        // given
        String memberId = "byrage";
        String password = "password";

        // when
        boolean result = memberService.signUp(memberId, password);
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(IllegalStateException::new);

        // then
        assertThat(result).isTrue();
        assertThat(member).isNotNull().satisfies(m -> {
            assertThat(m.getId()).isNotNull();
            assertThat(m.getMemberId()).isEqualTo(memberId);
            assertThat(m.getPassword()).isEqualTo(PasswordUtils.encode(password));
        });
    }

    @Test
    @DisplayName("회원가입시 회원 ID가 이미 존재한다면 예외가 발생한다.")
    void signUpFailWhenAlreadyExists() {
        // given
        String memberId = "byrage";
        String password = "password";
        memberService.signUp(memberId, password);

        // when & then
        assertThatThrownBy(() -> memberService.signUp(memberId, password))
                .isInstanceOf(ExistsMemberException.class);
    }

    @Test
    @DisplayName("로그인")
    void signIn() {
        // given
        String memberId = "byrage";
        String password = "password";
        memberRepository.save(buildMember(memberId, password));

        // when
        boolean result = memberService.signIn(memberId, password);

        // then
        assertThat(result).isTrue();
    }

    private Member buildMember(String memberId, String password) {
        return Member.builder()
                .memberId(memberId)
                .password(password)
                .build();
    }
}