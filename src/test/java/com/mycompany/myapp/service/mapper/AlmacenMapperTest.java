package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlmacenMapperTest {

    private AlmacenMapper almacenMapper;

    @BeforeEach
    public void setUp() {
        almacenMapper = new AlmacenMapperImpl();
    }
}
