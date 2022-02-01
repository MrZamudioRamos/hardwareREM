package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComponenteMapperTest {

    private ComponenteMapper componenteMapper;

    @BeforeEach
    public void setUp() {
        componenteMapper = new ComponenteMapperImpl();
    }
}
