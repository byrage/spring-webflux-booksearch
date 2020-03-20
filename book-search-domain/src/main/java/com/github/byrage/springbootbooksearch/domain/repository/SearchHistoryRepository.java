package com.github.byrage.springbootbooksearch.domain.repository;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    Page<SearchHistory> findAllByMemberIdOrderBySearchDateDesc(Long memberId, Pageable pageable);

    @Query("select sh.searchKeyword as searchKeyword, count(sh) as count \n"
            + "from SearchHistory sh \n"
            + "group by sh.searchKeyword \n"
            + "order by count(sh) desc")
    Page<PopulateSearchKeyword> findPopulateSearchKeywords(PageRequest pageRequest);
}
