package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IvaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IvaDTO.class);
        IvaDTO ivaDTO1 = new IvaDTO();
        ivaDTO1.setId(1L);
        IvaDTO ivaDTO2 = new IvaDTO();
        assertThat(ivaDTO1).isNotEqualTo(ivaDTO2);
        ivaDTO2.setId(ivaDTO1.getId());
        assertThat(ivaDTO1).isEqualTo(ivaDTO2);
        ivaDTO2.setId(2L);
        assertThat(ivaDTO1).isNotEqualTo(ivaDTO2);
        ivaDTO1.setId(null);
        assertThat(ivaDTO1).isNotEqualTo(ivaDTO2);
    }
}
