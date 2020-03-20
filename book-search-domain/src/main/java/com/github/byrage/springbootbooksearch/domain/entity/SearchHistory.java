package com.github.byrage.springbootbooksearch.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchHistory extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;
    @Column
    private String searchKeyword;
    @Column
    private LocalDateTime searchDate;

    @Builder
    public SearchHistory(Member member, String searchKeyword, LocalDateTime searchDateTime) {
        this.member = member;
        this.searchKeyword = searchKeyword;
        this.searchDate = searchDateTime;
    }
}
