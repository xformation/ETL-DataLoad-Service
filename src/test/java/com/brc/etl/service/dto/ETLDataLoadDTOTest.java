package com.brc.etl.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.brc.etl.web.rest.TestUtil;

public class ETLDataLoadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ETLDataLoadDTO.class);
        ETLDataLoadDTO eTLDataLoadDTO1 = new ETLDataLoadDTO();
        eTLDataLoadDTO1.setId(1L);
        ETLDataLoadDTO eTLDataLoadDTO2 = new ETLDataLoadDTO();
        assertThat(eTLDataLoadDTO1).isNotEqualTo(eTLDataLoadDTO2);
        eTLDataLoadDTO2.setId(eTLDataLoadDTO1.getId());
        assertThat(eTLDataLoadDTO1).isEqualTo(eTLDataLoadDTO2);
        eTLDataLoadDTO2.setId(2L);
        assertThat(eTLDataLoadDTO1).isNotEqualTo(eTLDataLoadDTO2);
        eTLDataLoadDTO1.setId(null);
        assertThat(eTLDataLoadDTO1).isNotEqualTo(eTLDataLoadDTO2);
    }
}
