package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComponenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComponenteDTO.class);
        ComponenteDTO componenteDTO1 = new ComponenteDTO();
        componenteDTO1.setId(1L);
        ComponenteDTO componenteDTO2 = new ComponenteDTO();
        assertThat(componenteDTO1).isNotEqualTo(componenteDTO2);
        componenteDTO2.setId(componenteDTO1.getId());
        assertThat(componenteDTO1).isEqualTo(componenteDTO2);
        componenteDTO2.setId(2L);
        assertThat(componenteDTO1).isNotEqualTo(componenteDTO2);
        componenteDTO1.setId(null);
        assertThat(componenteDTO1).isNotEqualTo(componenteDTO2);
    }
}
