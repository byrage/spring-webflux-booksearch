package com.github.byrage.springbootbooksearch.domain.repository;

import com.github.byrage.springbootbooksearch.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
