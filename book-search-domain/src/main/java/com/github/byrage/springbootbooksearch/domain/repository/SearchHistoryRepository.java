package com.github.byrage.springbootbooksearch.domain.repository;

import com.github.byrage.springbootbooksearch.domain.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findAllByMemberIdOrderBySearchDateDesc(Long memberId);
}
