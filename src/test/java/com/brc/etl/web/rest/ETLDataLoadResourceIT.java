package com.brc.etl.web.rest;

import com.brc.etl.EtldataloadserviceApp;
import com.brc.etl.domain.ETLDataLoad;
import com.brc.etl.repository.ETLDataLoadRepository;
import com.brc.etl.service.ETLDataLoadService;
import com.brc.etl.service.dto.ETLDataLoadDTO;
import com.brc.etl.service.mapper.ETLDataLoadMapper;

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
 * Integration tests for the {@link ETLDataLoadResource} REST controller.
 */
@SpringBootTest(classes = EtldataloadserviceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ETLDataLoadResourceIT {

    private static final Long DEFAULT_RECORD_ID = 1L;
    private static final Long UPDATED_RECORD_ID = 2L;

    private static final String DEFAULT_DATA_FLOW_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_FLOW_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_DATA_LOAD_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_DATA_LOAD_DATE = "BBBBBBBBBB";

    private static final Long DEFAULT_RECORD_COUNTS = 1L;
    private static final Long UPDATED_RECORD_COUNTS = 2L;

    private static final String DEFAULT_DATA_LOAD_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_DATA_LOAD_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATE_TS = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_TS = "BBBBBBBBBB";

    @Autowired
    private ETLDataLoadRepository eTLDataLoadRepository;

    @Autowired
    private ETLDataLoadMapper eTLDataLoadMapper;

    @Autowired
    private ETLDataLoadService eTLDataLoadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restETLDataLoadMockMvc;

    private ETLDataLoad eTLDataLoad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ETLDataLoad createEntity(EntityManager em) {
        ETLDataLoad eTLDataLoad = new ETLDataLoad()
            .recordId(DEFAULT_RECORD_ID)
            .dataFlowType(DEFAULT_DATA_FLOW_TYPE)
            .dataType(DEFAULT_DATA_TYPE)
            .lastDataLoadDate(DEFAULT_LAST_DATA_LOAD_DATE)
            .recordCounts(DEFAULT_RECORD_COUNTS)
            .dataLoadFrequency(DEFAULT_DATA_LOAD_FREQUENCY)
            .updateTs(DEFAULT_UPDATE_TS);
        return eTLDataLoad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ETLDataLoad createUpdatedEntity(EntityManager em) {
        ETLDataLoad eTLDataLoad = new ETLDataLoad()
            .recordId(UPDATED_RECORD_ID)
            .dataFlowType(UPDATED_DATA_FLOW_TYPE)
            .dataType(UPDATED_DATA_TYPE)
            .lastDataLoadDate(UPDATED_LAST_DATA_LOAD_DATE)
            .recordCounts(UPDATED_RECORD_COUNTS)
            .dataLoadFrequency(UPDATED_DATA_LOAD_FREQUENCY)
            .updateTs(UPDATED_UPDATE_TS);
        return eTLDataLoad;
    }

    @BeforeEach
    public void initTest() {
        eTLDataLoad = createEntity(em);
    }

    @Test
    @Transactional
    public void createETLDataLoad() throws Exception {
        int databaseSizeBeforeCreate = eTLDataLoadRepository.findAll().size();
        // Create the ETLDataLoad
        ETLDataLoadDTO eTLDataLoadDTO = eTLDataLoadMapper.toDto(eTLDataLoad);
        restETLDataLoadMockMvc.perform(post("/api/etl-data-loads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLDataLoadDTO)))
            .andExpect(status().isCreated());

        // Validate the ETLDataLoad in the database
        List<ETLDataLoad> eTLDataLoadList = eTLDataLoadRepository.findAll();
        assertThat(eTLDataLoadList).hasSize(databaseSizeBeforeCreate + 1);
        ETLDataLoad testETLDataLoad = eTLDataLoadList.get(eTLDataLoadList.size() - 1);
        assertThat(testETLDataLoad.getRecordId()).isEqualTo(DEFAULT_RECORD_ID);
        assertThat(testETLDataLoad.getDataFlowType()).isEqualTo(DEFAULT_DATA_FLOW_TYPE);
        assertThat(testETLDataLoad.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testETLDataLoad.getLastDataLoadDate()).isEqualTo(DEFAULT_LAST_DATA_LOAD_DATE);
        assertThat(testETLDataLoad.getRecordCounts()).isEqualTo(DEFAULT_RECORD_COUNTS);
        assertThat(testETLDataLoad.getDataLoadFrequency()).isEqualTo(DEFAULT_DATA_LOAD_FREQUENCY);
        assertThat(testETLDataLoad.getUpdateTs()).isEqualTo(DEFAULT_UPDATE_TS);
    }

    @Test
    @Transactional
    public void createETLDataLoadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eTLDataLoadRepository.findAll().size();

        // Create the ETLDataLoad with an existing ID
        eTLDataLoad.setRecordId(1L);
        ETLDataLoadDTO eTLDataLoadDTO = eTLDataLoadMapper.toDto(eTLDataLoad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restETLDataLoadMockMvc.perform(post("/api/etl-data-loads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLDataLoadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ETLDataLoad in the database
        List<ETLDataLoad> eTLDataLoadList = eTLDataLoadRepository.findAll();
        assertThat(eTLDataLoadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllETLDataLoads() throws Exception {
        // Initialize the database
        eTLDataLoadRepository.saveAndFlush(eTLDataLoad);

        // Get all the eTLDataLoadList
        restETLDataLoadMockMvc.perform(get("/api/etl-data-loads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eTLDataLoad.getRecordId().intValue())))
            .andExpect(jsonPath("$.[*].recordId").value(hasItem(DEFAULT_RECORD_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFlowType").value(hasItem(DEFAULT_DATA_FLOW_TYPE)))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].lastDataLoadDate").value(hasItem(DEFAULT_LAST_DATA_LOAD_DATE)))
            .andExpect(jsonPath("$.[*].recordCounts").value(hasItem(DEFAULT_RECORD_COUNTS.intValue())))
            .andExpect(jsonPath("$.[*].dataLoadFrequency").value(hasItem(DEFAULT_DATA_LOAD_FREQUENCY)))
            .andExpect(jsonPath("$.[*].updateTs").value(hasItem(DEFAULT_UPDATE_TS)));
    }
    
    @Test
    @Transactional
    public void getETLDataLoad() throws Exception {
        // Initialize the database
        eTLDataLoadRepository.saveAndFlush(eTLDataLoad);

        // Get the eTLDataLoad
        restETLDataLoadMockMvc.perform(get("/api/etl-data-loads/{id}", eTLDataLoad.getRecordId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eTLDataLoad.getRecordId().intValue()))
            .andExpect(jsonPath("$.recordId").value(DEFAULT_RECORD_ID.intValue()))
            .andExpect(jsonPath("$.dataFlowType").value(DEFAULT_DATA_FLOW_TYPE))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE))
            .andExpect(jsonPath("$.lastDataLoadDate").value(DEFAULT_LAST_DATA_LOAD_DATE))
            .andExpect(jsonPath("$.recordCounts").value(DEFAULT_RECORD_COUNTS.intValue()))
            .andExpect(jsonPath("$.dataLoadFrequency").value(DEFAULT_DATA_LOAD_FREQUENCY))
            .andExpect(jsonPath("$.updateTs").value(DEFAULT_UPDATE_TS));
    }
    @Test
    @Transactional
    public void getNonExistingETLDataLoad() throws Exception {
        // Get the eTLDataLoad
        restETLDataLoadMockMvc.perform(get("/api/etl-data-loads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateETLDataLoad() throws Exception {
        // Initialize the database
        eTLDataLoadRepository.saveAndFlush(eTLDataLoad);

        int databaseSizeBeforeUpdate = eTLDataLoadRepository.findAll().size();

        // Update the eTLDataLoad
        ETLDataLoad updatedETLDataLoad = eTLDataLoadRepository.findById(eTLDataLoad.getRecordId()).get();
        // Disconnect from session so that the updates on updatedETLDataLoad are not directly saved in db
        em.detach(updatedETLDataLoad);
        updatedETLDataLoad
            .recordId(UPDATED_RECORD_ID)
            .dataFlowType(UPDATED_DATA_FLOW_TYPE)
            .dataType(UPDATED_DATA_TYPE)
            .lastDataLoadDate(UPDATED_LAST_DATA_LOAD_DATE)
            .recordCounts(UPDATED_RECORD_COUNTS)
            .dataLoadFrequency(UPDATED_DATA_LOAD_FREQUENCY)
            .updateTs(UPDATED_UPDATE_TS);
        ETLDataLoadDTO eTLDataLoadDTO = eTLDataLoadMapper.toDto(updatedETLDataLoad);

        restETLDataLoadMockMvc.perform(put("/api/etl-data-loads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLDataLoadDTO)))
            .andExpect(status().isOk());

        // Validate the ETLDataLoad in the database
        List<ETLDataLoad> eTLDataLoadList = eTLDataLoadRepository.findAll();
        assertThat(eTLDataLoadList).hasSize(databaseSizeBeforeUpdate);
        ETLDataLoad testETLDataLoad = eTLDataLoadList.get(eTLDataLoadList.size() - 1);
        assertThat(testETLDataLoad.getRecordId()).isEqualTo(UPDATED_RECORD_ID);
        assertThat(testETLDataLoad.getDataFlowType()).isEqualTo(UPDATED_DATA_FLOW_TYPE);
        assertThat(testETLDataLoad.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testETLDataLoad.getLastDataLoadDate()).isEqualTo(UPDATED_LAST_DATA_LOAD_DATE);
        assertThat(testETLDataLoad.getRecordCounts()).isEqualTo(UPDATED_RECORD_COUNTS);
        assertThat(testETLDataLoad.getDataLoadFrequency()).isEqualTo(UPDATED_DATA_LOAD_FREQUENCY);
        assertThat(testETLDataLoad.getUpdateTs()).isEqualTo(UPDATED_UPDATE_TS);
    }

    @Test
    @Transactional
    public void updateNonExistingETLDataLoad() throws Exception {
        int databaseSizeBeforeUpdate = eTLDataLoadRepository.findAll().size();

        // Create the ETLDataLoad
        ETLDataLoadDTO eTLDataLoadDTO = eTLDataLoadMapper.toDto(eTLDataLoad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restETLDataLoadMockMvc.perform(put("/api/etl-data-loads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eTLDataLoadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ETLDataLoad in the database
        List<ETLDataLoad> eTLDataLoadList = eTLDataLoadRepository.findAll();
        assertThat(eTLDataLoadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteETLDataLoad() throws Exception {
        // Initialize the database
        eTLDataLoadRepository.saveAndFlush(eTLDataLoad);

        int databaseSizeBeforeDelete = eTLDataLoadRepository.findAll().size();

        // Delete the eTLDataLoad
        restETLDataLoadMockMvc.perform(delete("/api/etl-data-loads/{id}", eTLDataLoad.getRecordId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ETLDataLoad> eTLDataLoadList = eTLDataLoadRepository.findAll();
        assertThat(eTLDataLoadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
