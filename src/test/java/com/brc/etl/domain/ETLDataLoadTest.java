package com.brc.etl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.brc.etl.web.rest.TestUtil;

public class ETLDataLoadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ETLDataLoad.class);
        ETLDataLoad eTLDataLoad1 = new ETLDataLoad();
//        eTLDataLoad1.setId(1L);
        ETLDataLoad eTLDataLoad2 = new ETLDataLoad();
//        eTLDataLoad2.setId(eTLDataLoad1.getId());
        assertThat(eTLDataLoad1).isEqualTo(eTLDataLoad2);
//        eTLDataLoad2.setId(2L);
        assertThat(eTLDataLoad1).isNotEqualTo(eTLDataLoad2);
//        eTLDataLoad1.setId(null);
        assertThat(eTLDataLoad1).isNotEqualTo(eTLDataLoad2);
    }
}
