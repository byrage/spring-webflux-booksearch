package com.github.byrage.springbootbooksearch.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String password;

    @Builder
    public Member(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
