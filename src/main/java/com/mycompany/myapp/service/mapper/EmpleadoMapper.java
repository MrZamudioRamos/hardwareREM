package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.service.dto.EmpleadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class, EmpresaMapper.class })
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    EmpleadoDTO toDto(Empleado s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    EmpleadoDTO toDtoId(Empleado empleado);
}
