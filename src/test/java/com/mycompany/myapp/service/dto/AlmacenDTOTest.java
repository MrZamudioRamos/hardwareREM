package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlmacenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlmacenDTO.class);
        AlmacenDTO almacenDTO1 = new AlmacenDTO();
        almacenDTO1.setId(1L);
        AlmacenDTO almacenDTO2 = new AlmacenDTO();
        assertThat(almacenDTO1).isNotEqualTo(almacenDTO2);
        almacenDTO2.setId(almacenDTO1.getId());
        assertThat(almacenDTO1).isEqualTo(almacenDTO2);
        almacenDTO2.setId(2L);
        assertThat(almacenDTO1).isNotEqualTo(almacenDTO2);
        almacenDTO1.setId(null);
        assertThat(almacenDTO1).isNotEqualTo(almacenDTO2);
    }
}
