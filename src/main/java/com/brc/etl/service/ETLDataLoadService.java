package com.brc.etl.service;

import com.brc.etl.service.dto.ETLDataLoadDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.brc.etl.domain.ETLDataLoad}.
 */
public interface ETLDataLoadService {

    /**
     * Save a eTLDataLoad.
     *
     * @param eTLDataLoadDTO the entity to save.
     * @return the persisted entity.
     */
    ETLDataLoadDTO save(ETLDataLoadDTO eTLDataLoadDTO);

    /**
     * Get all the eTLDataLoads.
     *
     * @return the list of entities.
     */
    List<ETLDataLoadDTO> findAll();


    /**
     * Get the "id" eTLDataLoad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ETLDataLoadDTO> findOne(Long id);

    /**
     * Delete the "id" eTLDataLoad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
