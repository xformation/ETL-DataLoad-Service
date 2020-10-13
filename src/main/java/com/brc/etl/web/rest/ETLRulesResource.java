package com.brc.etl.web.rest;

import com.brc.etl.service.ETLRulesService;
import com.brc.etl.web.rest.errors.BadRequestAlertException;
import com.brc.etl.service.dto.ETLRulesDTO;

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
 * REST controller for managing {@link com.brc.etl.domain.ETLRules}.
 */
@RestController
@RequestMapping("/api")
public class ETLRulesResource {

    private final Logger log = LoggerFactory.getLogger(ETLRulesResource.class);

    private static final String ENTITY_NAME = "etldataloadserviceEtlRules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ETLRulesService eTLRulesService;

    public ETLRulesResource(ETLRulesService eTLRulesService) {
        this.eTLRulesService = eTLRulesService;
    }

    /**
     * {@code POST  /etl-rules} : Create a new eTLRules.
     *
     * @param eTLRulesDTO the eTLRulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eTLRulesDTO, or with status {@code 400 (Bad Request)} if the eTLRules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etl-rules")
    public ResponseEntity<ETLRulesDTO> createETLRules(@RequestBody ETLRulesDTO eTLRulesDTO) throws URISyntaxException {
        log.debug("REST request to save ETLRules : {}", eTLRulesDTO);
        if (eTLRulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new eTLRules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ETLRulesDTO result = eTLRulesService.save(eTLRulesDTO);
        return ResponseEntity.created(new URI("/api/etl-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etl-rules} : Updates an existing eTLRules.
     *
     * @param eTLRulesDTO the eTLRulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eTLRulesDTO,
     * or with status {@code 400 (Bad Request)} if the eTLRulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eTLRulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etl-rules")
    public ResponseEntity<ETLRulesDTO> updateETLRules(@RequestBody ETLRulesDTO eTLRulesDTO) throws URISyntaxException {
        log.debug("REST request to update ETLRules : {}", eTLRulesDTO);
        if (eTLRulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ETLRulesDTO result = eTLRulesService.save(eTLRulesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eTLRulesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etl-rules} : get all the eTLRules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eTLRules in body.
     */
    @GetMapping("/etl-rules")
    public List<ETLRulesDTO> getAllETLRules() {
        log.debug("REST request to get all ETLRules");
        return eTLRulesService.findAll();
    }

    /**
     * {@code GET  /etl-rules/:id} : get the "id" eTLRules.
     *
     * @param id the id of the eTLRulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eTLRulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etl-rules/{id}")
    public ResponseEntity<ETLRulesDTO> getETLRules(@PathVariable Long id) {
        log.debug("REST request to get ETLRules : {}", id);
        Optional<ETLRulesDTO> eTLRulesDTO = eTLRulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eTLRulesDTO);
    }

    /**
     * {@code DELETE  /etl-rules/:id} : delete the "id" eTLRules.
     *
     * @param id the id of the eTLRulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etl-rules/{id}")
    public ResponseEntity<Void> deleteETLRules(@PathVariable Long id) {
        log.debug("REST request to delete ETLRules : {}", id);
        eTLRulesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
