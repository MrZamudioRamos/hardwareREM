package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadoMapperTest {

    private EmpleadoMapper empleadoMapper;

    @BeforeEach
    public void setUp() {
        empleadoMapper = new EmpleadoMapperImpl();
    }
}
