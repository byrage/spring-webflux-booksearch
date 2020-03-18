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
    /**
     * Member 테이블의 id. ex) 1,2,3,...
     */
    @Column
    private Long memberId;
    @Column
    private String searchKeyword;
    @Column
    private LocalDateTime searchDate;

    @Builder
    public SearchHistory(Long memberId, String searchKeyword, LocalDateTime searchDate) {
        this.memberId = memberId;
        this.searchKeyword = searchKeyword;
        this.searchDate = searchDate;
    }
}
