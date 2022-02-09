package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Cliente;
import com.mycompany.myapp.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmpresaMapper.class })
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "id")
    ClienteDTO toDto(Cliente s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "dni", source = "dni")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "apellidos", source = "apellidos")
    @Mapping(target = "telefono", source = "telefono")
    @Mapping(target = "mail", source = "mail")
    @Mapping(target = "pais", source = "pais")
    @Mapping(target = "provincia", source = "provincia")
    @Mapping(target = "codigoPostal", source = "codigoPostal")
    @Mapping(target = "calle", source = "calle")
    ClienteDTO toDtoId(Cliente cliente);
}
