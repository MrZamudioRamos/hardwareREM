package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IvaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Iva.class);
        Iva iva1 = new Iva();
        iva1.setId(1L);
        Iva iva2 = new Iva();
        iva2.setId(iva1.getId());
        assertThat(iva1).isEqualTo(iva2);
        iva2.setId(2L);
        assertThat(iva1).isNotEqualTo(iva2);
        iva1.setId(null);
        assertThat(iva1).isNotEqualTo(iva2);
    }
}
