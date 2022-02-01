package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComponenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Componente.class);
        Componente componente1 = new Componente();
        componente1.setId(1L);
        Componente componente2 = new Componente();
        componente2.setId(componente1.getId());
        assertThat(componente1).isEqualTo(componente2);
        componente2.setId(2L);
        assertThat(componente1).isNotEqualTo(componente2);
        componente1.setId(null);
        assertThat(componente1).isNotEqualTo(componente2);
    }
}
