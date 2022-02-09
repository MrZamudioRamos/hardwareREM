package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombreSocial", source = "nombreSocial")
    @Mapping(target = "cif", source = "cif")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "mail", source = "mail")
    @Mapping(target = "pais", source = "pais")
    @Mapping(target = "provincia", source = "provincia")
    @Mapping(target = "sucursal", source = "sucursal")
    @Mapping(target = "calle", source = "calle")
    @Mapping(target = "codigoPostal", source = "codigoPostal")
    EmpresaDTO toDtoId(Empresa empresa);
}
