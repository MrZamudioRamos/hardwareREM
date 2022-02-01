package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Iva;
import com.mycompany.myapp.service.dto.IvaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Iva} and its DTO {@link IvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IvaMapper extends EntityMapper<IvaDTO, Iva> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IvaDTO toDtoId(Iva iva);
}
