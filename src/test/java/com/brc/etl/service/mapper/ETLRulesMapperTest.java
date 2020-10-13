package com.brc.etl.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ETLRulesMapperTest {

    private ETLRulesMapper eTLRulesMapper;

    @BeforeEach
    public void setUp() {
//        eTLRulesMapper = new ETLRulesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(eTLRulesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eTLRulesMapper.fromId(null)).isNull();
    }
}
