package com.brc.etl.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.brc.etl.web.rest.TestUtil;

public class ETLRulesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ETLRulesDTO.class);
        ETLRulesDTO eTLRulesDTO1 = new ETLRulesDTO();
        eTLRulesDTO1.setId(1L);
        ETLRulesDTO eTLRulesDTO2 = new ETLRulesDTO();
        assertThat(eTLRulesDTO1).isNotEqualTo(eTLRulesDTO2);
        eTLRulesDTO2.setId(eTLRulesDTO1.getId());
        assertThat(eTLRulesDTO1).isEqualTo(eTLRulesDTO2);
        eTLRulesDTO2.setId(2L);
        assertThat(eTLRulesDTO1).isNotEqualTo(eTLRulesDTO2);
        eTLRulesDTO1.setId(null);
        assertThat(eTLRulesDTO1).isNotEqualTo(eTLRulesDTO2);
    }
}
