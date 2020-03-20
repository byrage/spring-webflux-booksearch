package com.github.byrage.springbootbooksearch.domain.entity;

import com.github.byrage.springbootbooksearch.domain.util.PasswordUtils;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    /**
     * Member 테이블의 id. ex) 1,2,3,...
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 회원 id. ex) byrage
     */
    @Column
    private String memberId;
    @Column
    private String password;

    @Builder
    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = PasswordUtils.encode(password);
    }

    public boolean isMatchedPassword(String rawPassword) {
        return PasswordUtils.isEqualsEncodedValue(rawPassword, password);
    }
}
