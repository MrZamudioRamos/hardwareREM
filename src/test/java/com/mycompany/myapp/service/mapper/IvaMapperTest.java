package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IvaMapperTest {

    private IvaMapper ivaMapper;

    @BeforeEach
    public void setUp() {
        ivaMapper = new IvaMapperImpl();
    }
}
