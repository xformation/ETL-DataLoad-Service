package com.brc.etl.service.impl;

import com.brc.etl.service.ETLDataLoadService;
import com.brc.etl.domain.ETLDataLoad;
import com.brc.etl.repository.ETLDataLoadRepository;
import com.brc.etl.service.dto.ETLDataLoadDTO;
import com.brc.etl.service.mapper.ETLDataLoadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ETLDataLoad}.
 */
@Service
@Transactional
public class ETLDataLoadServiceImpl implements ETLDataLoadService {

    private final Logger log = LoggerFactory.getLogger(ETLDataLoadServiceImpl.class);

    private final ETLDataLoadRepository eTLDataLoadRepository;

    private final ETLDataLoadMapper eTLDataLoadMapper;

    public ETLDataLoadServiceImpl(ETLDataLoadRepository eTLDataLoadRepository, ETLDataLoadMapper eTLDataLoadMapper) {
        this.eTLDataLoadRepository = eTLDataLoadRepository;
        this.eTLDataLoadMapper = eTLDataLoadMapper;
    }

    @Override
    public ETLDataLoadDTO save(ETLDataLoadDTO eTLDataLoadDTO) {
        log.debug("Request to save ETLDataLoad : {}", eTLDataLoadDTO);
        ETLDataLoad eTLDataLoad = eTLDataLoadMapper.toEntity(eTLDataLoadDTO);
        eTLDataLoad = eTLDataLoadRepository.save(eTLDataLoad);
        return eTLDataLoadMapper.toDto(eTLDataLoad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ETLDataLoadDTO> findAll() {
        log.debug("Request to get all ETLDataLoads");
        return eTLDataLoadRepository.findAll().stream()
            .map(eTLDataLoadMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ETLDataLoadDTO> findOne(Long id) {
        log.debug("Request to get ETLDataLoad : {}", id);
        return eTLDataLoadRepository.findById(id)
            .map(eTLDataLoadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ETLDataLoad : {}", id);
        eTLDataLoadRepository.deleteById(id);
    }
}
