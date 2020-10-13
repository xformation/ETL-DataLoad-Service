package com.brc.etl.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ETLDataLoadMapperTest {

    private ETLDataLoadMapper eTLDataLoadMapper;

    @BeforeEach
    public void setUp() {
//        eTLDataLoadMapper = new ETLDataLoadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
//        assertThat(eTLDataLoadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eTLDataLoadMapper.fromId(null)).isNull();
    }
}
