package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Almacen;
import com.mycompany.myapp.repository.AlmacenRepository;
import com.mycompany.myapp.service.AlmacenService;
import com.mycompany.myapp.service.dto.AlmacenDTO;
import com.mycompany.myapp.service.mapper.AlmacenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Almacen}.
 */
@Service
@Transactional
public class AlmacenServiceImpl implements AlmacenService {

    private final Logger log = LoggerFactory.getLogger(AlmacenServiceImpl.class);

    private final AlmacenRepository almacenRepository;

    private final AlmacenMapper almacenMapper;

    public AlmacenServiceImpl(AlmacenRepository almacenRepository, AlmacenMapper almacenMapper) {
        this.almacenRepository = almacenRepository;
        this.almacenMapper = almacenMapper;
    }

    @Override
    public AlmacenDTO save(AlmacenDTO almacenDTO) {
        log.debug("Request to save Almacen : {}", almacenDTO);
        Almacen almacen = almacenMapper.toEntity(almacenDTO);
        almacen = almacenRepository.save(almacen);
        return almacenMapper.toDto(almacen);
    }

    @Override
    public Optional<AlmacenDTO> partialUpdate(AlmacenDTO almacenDTO) {
        log.debug("Request to partially update Almacen : {}", almacenDTO);

        return almacenRepository
            .findById(almacenDTO.getId())
            .map(existingAlmacen -> {
                almacenMapper.partialUpdate(existingAlmacen, almacenDTO);

                return existingAlmacen;
            })
            .map(almacenRepository::save)
            .map(almacenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlmacenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Almacens");
        return almacenRepository.findAll(pageable).map(almacenMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlmacenDTO> findOne(Long id) {
        log.debug("Request to get Almacen : {}", id);
        return almacenRepository.findById(id).map(almacenMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Almacen : {}", id);
        almacenRepository.deleteById(id);
    }
}
