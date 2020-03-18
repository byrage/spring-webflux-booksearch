package com.github.byrage.springbootbooksearch.domain.repository;

import com.github.byrage.springbootbooksearch.domain.dto.PopulateSearchKeyword;
import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findAllByMemberIdOrderBySearchDateDesc(Long memberId);

    @Query("select sh.searchKeyword as searchKeyword, count(sh) as count \n"
            + "from SearchHistory sh \n"
            + "group by sh.searchKeyword \n"
            + "order by count(sh) desc")
    List<PopulateSearchKeyword> findPopulateSearchKeywords(PageRequest pageRequest);
}
