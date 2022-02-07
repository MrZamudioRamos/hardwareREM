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
    EmpresaDTO toDtoId(Empresa empresa);
}
