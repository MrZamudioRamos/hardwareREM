package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Almacen;
import com.mycompany.myapp.service.dto.AlmacenDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Almacen} and its DTO {@link AlmacenDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmpresaMapper.class })
public interface AlmacenMapper extends EntityMapper<AlmacenDTO, Almacen> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    AlmacenDTO toDto(Almacen s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlmacenDTO toDtoId(Almacen almacen);
}
