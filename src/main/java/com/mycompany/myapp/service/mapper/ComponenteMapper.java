package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Componente;
import com.mycompany.myapp.service.dto.ComponenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Componente} and its DTO {@link ComponenteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductoMapper.class })
public interface ComponenteMapper extends EntityMapper<ComponenteDTO, Componente> {
    @Mapping(target = "producto", source = "producto", qualifiedByName = "id")
    ComponenteDTO toDto(Componente s);
}
