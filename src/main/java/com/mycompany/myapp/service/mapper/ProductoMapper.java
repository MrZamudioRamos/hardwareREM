package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = { IvaMapper.class, EmpresaMapper.class, PedidoMapper.class, AlmacenMapper.class })
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {
    @Mapping(target = "iva", source = "iva", qualifiedByName = "id")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    @Mapping(target = "pedido", source = "pedido", qualifiedByName = "id")
    @Mapping(target = "almacen", source = "almacen", qualifiedByName = "id")
    ProductoDTO toDto(Producto s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoDTO toDtoId(Producto producto);
}
