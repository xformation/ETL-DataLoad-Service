package com.brc.etl.web.rest;

import com.brc.etl.service.ETLDataLoadService;
import com.brc.etl.web.rest.errors.BadRequestAlertException;
import com.brc.etl.service.dto.ETLDataLoadDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.brc.etl.domain.ETLDataLoad}.
 */
@RestController
@RequestMapping("/api")
public class ETLDataLoadResource {

    private final Logger log = LoggerFactory.getLogger(ETLDataLoadResource.class);

    private static final String ENTITY_NAME = "etldataloadserviceEtlDataLoad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ETLDataLoadService eTLDataLoadService;

    public ETLDataLoadResource(ETLDataLoadService eTLDataLoadService) {
        this.eTLDataLoadService = eTLDataLoadService;
    }

    /**
     * {@code POST  /etl-data-loads} : Create a new eTLDataLoad.
     *
     * @param eTLDataLoadDTO the eTLDataLoadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eTLDataLoadDTO, or with status {@code 400 (Bad Request)} if the eTLDataLoad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etl-data-loads")
    public ResponseEntity<ETLDataLoadDTO> createETLDataLoad(@RequestBody ETLDataLoadDTO eTLDataLoadDTO) throws URISyntaxException {
        log.debug("REST request to save ETLDataLoad : {}", eTLDataLoadDTO);
        if (eTLDataLoadDTO.getId() != null) {
            throw new BadRequestAlertException("A new eTLDataLoad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ETLDataLoadDTO result = eTLDataLoadService.save(eTLDataLoadDTO);
        return ResponseEntity.created(new URI("/api/etl-data-loads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etl-data-loads} : Updates an existing eTLDataLoad.
     *
     * @param eTLDataLoadDTO the eTLDataLoadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eTLDataLoadDTO,
     * or with status {@code 400 (Bad Request)} if the eTLDataLoadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eTLDataLoadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etl-data-loads")
    public ResponseEntity<ETLDataLoadDTO> updateETLDataLoad(@RequestBody ETLDataLoadDTO eTLDataLoadDTO) throws URISyntaxException {
        log.debug("REST request to update ETLDataLoad : {}", eTLDataLoadDTO);
        if (eTLDataLoadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ETLDataLoadDTO result = eTLDataLoadService.save(eTLDataLoadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eTLDataLoadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etl-data-loads} : get all the eTLDataLoads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eTLDataLoads in body.
     */
    @GetMapping("/etl-data-loads")
    public List<ETLDataLoadDTO> getAllETLDataLoads() {
        log.debug("REST request to get all ETLDataLoads");
        return eTLDataLoadService.findAll();
    }

    /**
     * {@code GET  /etl-data-loads/:id} : get the "id" eTLDataLoad.
     *
     * @param id the id of the eTLDataLoadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eTLDataLoadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etl-data-loads/{id}")
    public ResponseEntity<ETLDataLoadDTO> getETLDataLoad(@PathVariable Long id) {
        log.debug("REST request to get ETLDataLoad : {}", id);
        Optional<ETLDataLoadDTO> eTLDataLoadDTO = eTLDataLoadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eTLDataLoadDTO);
    }

    /**
     * {@code DELETE  /etl-data-loads/:id} : delete the "id" eTLDataLoad.
     *
     * @param id the id of the eTLDataLoadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etl-data-loads/{id}")
    public ResponseEntity<Void> deleteETLDataLoad(@PathVariable Long id) {
        log.debug("REST request to delete ETLDataLoad : {}", id);
        eTLDataLoadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
