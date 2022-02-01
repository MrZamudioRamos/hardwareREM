package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Factura;
import com.mycompany.myapp.service.dto.FacturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Factura} and its DTO {@link FacturaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ClienteMapper.class, EmpresaMapper.class })
public interface FacturaMapper extends EntityMapper<FacturaDTO, Factura> {
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "id")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    FacturaDTO toDto(Factura s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FacturaDTO toDtoId(Factura factura);
}
