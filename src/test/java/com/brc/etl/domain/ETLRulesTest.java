package com.brc.etl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.brc.etl.web.rest.TestUtil;

public class ETLRulesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ETLRules.class);
        ETLRules eTLRules1 = new ETLRules();
        eTLRules1.setId(1L);
        ETLRules eTLRules2 = new ETLRules();
        eTLRules2.setId(eTLRules1.getId());
        assertThat(eTLRules1).isEqualTo(eTLRules2);
        eTLRules2.setId(2L);
        assertThat(eTLRules1).isNotEqualTo(eTLRules2);
        eTLRules1.setId(null);
        assertThat(eTLRules1).isNotEqualTo(eTLRules2);
    }
}
