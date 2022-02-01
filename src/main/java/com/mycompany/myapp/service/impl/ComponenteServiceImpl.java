package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Componente;
import com.mycompany.myapp.repository.ComponenteRepository;
import com.mycompany.myapp.repository.specification.ComponenteSpecification;
import com.mycompany.myapp.service.ComponenteService;
import com.mycompany.myapp.service.dto.ComponenteDTO;
import com.mycompany.myapp.service.mapper.ComponenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Componente}.
 */
@Service
@Transactional
public class ComponenteServiceImpl implements ComponenteService {

    private final Logger log = LoggerFactory.getLogger(ComponenteServiceImpl.class);

    private final ComponenteRepository componenteRepository;

    private final ComponenteMapper componenteMapper;

    public ComponenteServiceImpl(ComponenteRepository componenteRepository, ComponenteMapper componenteMapper) {
        this.componenteRepository = componenteRepository;
        this.componenteMapper = componenteMapper;
    }

    @Override
    public ComponenteDTO save(ComponenteDTO componenteDTO) {
        log.debug("Request to save Componente : {}", componenteDTO);
        Componente componente = componenteMapper.toEntity(componenteDTO);
        componente = componenteRepository.save(componente);
        return componenteMapper.toDto(componente);
    }

    @Override
    public Optional<ComponenteDTO> partialUpdate(ComponenteDTO componenteDTO) {
        log.debug("Request to partially update Componente : {}", componenteDTO);

        return componenteRepository
            .findById(componenteDTO.getId())
            .map(existingComponente -> {
                componenteMapper.partialUpdate(existingComponente, componenteDTO);

                return existingComponente;
            })
            .map(componenteRepository::save)
            .map(componenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComponenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Componentes");
        return componenteRepository.findAll(pageable).map(componenteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComponenteDTO> findOne(Long id) {
        log.debug("Request to get Componente : {}", id);
        return componenteRepository.findById(id).map(componenteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Componente : {}", id);
        componenteRepository.deleteById(id);
    }

    public Page<ComponenteDTO> findAllBySearchingParam(String filtro, Pageable pageable) {
        log.debug("Filtro");
        return componenteRepository.findAll(ComponenteSpecification.searchingParam(filtro), pageable).map(componenteMapper::toDto);
    }
}
