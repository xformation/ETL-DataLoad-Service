package com.brc.etl.repository;

import com.brc.etl.domain.ETLDataLoad;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ETLDataLoad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ETLDataLoadRepository extends JpaRepository<ETLDataLoad, Long> {
}
