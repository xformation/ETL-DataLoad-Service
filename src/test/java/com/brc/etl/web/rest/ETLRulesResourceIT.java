package com.brc.etl.web.rest;

import com.brc.etl.EtldataloadserviceApp;
import com.brc.etl.domain.ETLRules;
import com.brc.etl.repository.ETLRulesRepository;
import com.brc.etl.service.ETLRulesService;
import com.brc.etl.service.dto.ETLRulesDTO;
import com.brc.etl.service.mapper.ETLRulesMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ETLRulesResource} REST controller.
 */
@SpringBootTest(classes = EtldataloadserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ETLRulesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_CUT_OFF_DAY = "AAAAAAAAAA";
    private static final String UPDATED_CUT_OFF_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_CUT_OFF_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CUT_OFF_TIME = "BBBBBBBBBB";

    @Autowired
    private ETLRulesRepository eTLRulesRepository;

    @Autowired
    private ETLRulesMapper eTLRulesMapper;

    @Autowired
    private ETLRulesService eTLRulesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restETLRulesMockMvc;

    private ETLRules eTLRules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ETLRules createEntity(EntityManager em) {
        ETLRules eTLRules = new ETLRules()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .frequency(DEFAULT_FREQUENCY)
            .cutOffDay(DEFAULT_CUT_OFF_DAY)
            .cutOffTime(DEFAULT_CUT_OFF_TIME);
        return eTLRules;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ETLRules createUpdatedEntity(EntityManager em) {
        ETLRules eTLRules = new ETLRules()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .cutOffDay(UPDATED_CUT_OFF_DAY)
            .cutOffTime(UPDATED_CUT_OFF_TIME);
        return eTLRules;
    }

    @BeforeEach
    public void initTest() {
        eTLRules = createEntity(em);
    }

    @Test
    @Transactional
    public void createETLRules() throws Exception {
        int databaseSizeBeforeCreate = eTLRulesRepository.findAll().size();
        // Create the ETLRules
        ETLRulesDTO eTLRulesDTO = eTLRulesMapper.toDto(eTLRules);
        restETLRulesMockMvc.perform(post("/api/etl-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLRulesDTO)))
            .andExpect(status().isCreated());

        // Validate the ETLRules in the database
        List<ETLRules> eTLRulesList = eTLRulesRepository.findAll();
        assertThat(eTLRulesList).hasSize(databaseSizeBeforeCreate + 1);
        ETLRules testETLRules = eTLRulesList.get(eTLRulesList.size() - 1);
        assertThat(testETLRules.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testETLRules.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testETLRules.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testETLRules.getCutOffDay()).isEqualTo(DEFAULT_CUT_OFF_DAY);
        assertThat(testETLRules.getCutOffTime()).isEqualTo(DEFAULT_CUT_OFF_TIME);
    }

    @Test
    @Transactional
    public void createETLRulesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eTLRulesRepository.findAll().size();

        // Create the ETLRules with an existing ID
        eTLRules.setId(1L);
        ETLRulesDTO eTLRulesDTO = eTLRulesMapper.toDto(eTLRules);

        // An entity with an existing ID cannot be created, so this API call must fail
        restETLRulesMockMvc.perform(post("/api/etl-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLRulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ETLRules in the database
        List<ETLRules> eTLRulesList = eTLRulesRepository.findAll();
        assertThat(eTLRulesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllETLRules() throws Exception {
        // Initialize the database
        eTLRulesRepository.saveAndFlush(eTLRules);

        // Get all the eTLRulesList
        restETLRulesMockMvc.perform(get("/api/etl-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eTLRules.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].cutOffDay").value(hasItem(DEFAULT_CUT_OFF_DAY)))
            .andExpect(jsonPath("$.[*].cutOffTime").value(hasItem(DEFAULT_CUT_OFF_TIME)));
    }
    
    @Test
    @Transactional
    public void getETLRules() throws Exception {
        // Initialize the database
        eTLRulesRepository.saveAndFlush(eTLRules);

        // Get the eTLRules
        restETLRulesMockMvc.perform(get("/api/etl-rules/{id}", eTLRules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eTLRules.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.cutOffDay").value(DEFAULT_CUT_OFF_DAY))
            .andExpect(jsonPath("$.cutOffTime").value(DEFAULT_CUT_OFF_TIME));
    }
    @Test
    @Transactional
    public void getNonExistingETLRules() throws Exception {
        // Get the eTLRules
        restETLRulesMockMvc.perform(get("/api/etl-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateETLRules() throws Exception {
        // Initialize the database
        eTLRulesRepository.saveAndFlush(eTLRules);

        int databaseSizeBeforeUpdate = eTLRulesRepository.findAll().size();

        // Update the eTLRules
        ETLRules updatedETLRules = eTLRulesRepository.findById(eTLRules.getId()).get();
        // Disconnect from session so that the updates on updatedETLRules are not directly saved in db
        em.detach(updatedETLRules);
        updatedETLRules
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .cutOffDay(UPDATED_CUT_OFF_DAY)
            .cutOffTime(UPDATED_CUT_OFF_TIME);
        ETLRulesDTO eTLRulesDTO = eTLRulesMapper.toDto(updatedETLRules);

        restETLRulesMockMvc.perform(put("/api/etl-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLRulesDTO)))
            .andExpect(status().isOk());

        // Validate the ETLRules in the database
        List<ETLRules> eTLRulesList = eTLRulesRepository.findAll();
        assertThat(eTLRulesList).hasSize(databaseSizeBeforeUpdate);
        ETLRules testETLRules = eTLRulesList.get(eTLRulesList.size() - 1);
        assertThat(testETLRules.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testETLRules.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testETLRules.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testETLRules.getCutOffDay()).isEqualTo(UPDATED_CUT_OFF_DAY);
        assertThat(testETLRules.getCutOffTime()).isEqualTo(UPDATED_CUT_OFF_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingETLRules() throws Exception {
        int databaseSizeBeforeUpdate = eTLRulesRepository.findAll().size();

        // Create the ETLRules
        ETLRulesDTO eTLRulesDTO = eTLRulesMapper.toDto(eTLRules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restETLRulesMockMvc.perform(put("/api/etl-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLRulesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ETLRules in the database
        List<ETLRules> eTLRulesList = eTLRulesRepository.findAll();
        assertThat(eTLRulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteETLRules() throws Exception {
        // Initialize the database
        eTLRulesRepository.saveAndFlush(eTLRules);

        int databaseSizeBeforeDelete = eTLRulesRepository.findAll().size();

        // Delete the eTLRules
        restETLRulesMockMvc.perform(delete("/api/etl-rules/{id}", eTLRules.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ETLRules> eTLRulesList = eTLRulesRepository.findAll();
        assertThat(eTLRulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
