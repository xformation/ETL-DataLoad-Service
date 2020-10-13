package com.brc.etl.repository;

import com.brc.etl.domain.ETLRules;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ETLRules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ETLRulesRepository extends JpaRepository<ETLRules, Long> {
}
