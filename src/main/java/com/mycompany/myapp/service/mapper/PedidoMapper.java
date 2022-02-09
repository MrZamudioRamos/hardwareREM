package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.service.dto.PedidoDTO;
import com.mycompany.myapp.service.mapper.*;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring", uses = { FacturaMapper.class, EmpleadoMapper.class, EmpresaMapper.class, AlmacenMapper.class })
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {
    @Mapping(target = "factura", source = "factura", qualifiedByName = "id")
    @Mapping(target = "empleado", source = "empleado", qualifiedByName = "id")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    @Mapping(target = "almacen", source = "almacen", qualifiedByName = "id")
    PedidoDTO toDto(Pedido s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PedidoDTO toDtoId(Pedido pedido);
}
