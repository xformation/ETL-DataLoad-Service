package com.brc.etl.service.impl;

import com.brc.etl.service.ETLRulesService;
import com.brc.etl.domain.ETLRules;
import com.brc.etl.repository.ETLRulesRepository;
import com.brc.etl.service.dto.ETLRulesDTO;
import com.brc.etl.service.mapper.ETLRulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ETLRules}.
 */
@Service
@Transactional
public class ETLRulesServiceImpl implements ETLRulesService {

    private final Logger log = LoggerFactory.getLogger(ETLRulesServiceImpl.class);

    private final ETLRulesRepository eTLRulesRepository;

    private final ETLRulesMapper eTLRulesMapper;

    public ETLRulesServiceImpl(ETLRulesRepository eTLRulesRepository, ETLRulesMapper eTLRulesMapper) {
        this.eTLRulesRepository = eTLRulesRepository;
        this.eTLRulesMapper = eTLRulesMapper;
    }

    @Override
    public ETLRulesDTO save(ETLRulesDTO eTLRulesDTO) {
        log.debug("Request to save ETLRules : {}", eTLRulesDTO);
        ETLRules eTLRules = eTLRulesMapper.toEntity(eTLRulesDTO);
        eTLRules = eTLRulesRepository.save(eTLRules);
        return eTLRulesMapper.toDto(eTLRules);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ETLRulesDTO> findAll() {
        log.debug("Request to get all ETLRules");
        return eTLRulesRepository.findAll().stream()
            .map(eTLRulesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ETLRulesDTO> findOne(Long id) {
        log.debug("Request to get ETLRules : {}", id);
        return eTLRulesRepository.findById(id)
            .map(eTLRulesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ETLRules : {}", id);
        eTLRulesRepository.deleteById(id);
    }
}
