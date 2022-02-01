package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;

    @BeforeEach
    public void setUp() {
        pedidoMapper = new PedidoMapperImpl();
    }
}
