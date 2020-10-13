package com.brc.etl.service;

import com.brc.etl.service.dto.ETLRulesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.brc.etl.domain.ETLRules}.
 */
public interface ETLRulesService {

    /**
     * Save a eTLRules.
     *
     * @param eTLRulesDTO the entity to save.
     * @return the persisted entity.
     */
    ETLRulesDTO save(ETLRulesDTO eTLRulesDTO);

    /**
     * Get all the eTLRules.
     *
     * @return the list of entities.
     */
    List<ETLRulesDTO> findAll();


    /**
     * Get the "id" eTLRules.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ETLRulesDTO> findOne(Long id);

    /**
     * Delete the "id" eTLRules.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
