package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FacturaMapperTest {

    private FacturaMapper facturaMapper;

    @BeforeEach
    public void setUp() {
        facturaMapper = new FacturaMapperImpl();
    }
}
