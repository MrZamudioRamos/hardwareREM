package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AlmacenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Almacen.class);
        Almacen almacen1 = new Almacen();
        almacen1.setId(1L);
        Almacen almacen2 = new Almacen();
        almacen2.setId(almacen1.getId());
        assertThat(almacen1).isEqualTo(almacen2);
        almacen2.setId(2L);
        assertThat(almacen1).isNotEqualTo(almacen2);
        almacen1.setId(null);
        assertThat(almacen1).isNotEqualTo(almacen2);
    }
}
