package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Empresa;
import com.mycompany.myapp.repository.EmpresaRepository;
import com.mycompany.myapp.service.EmpresaService;
import com.mycompany.myapp.service.dto.EmpresaDTO;
import com.mycompany.myapp.service.mapper.EmpresaMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Empresa}.
 */
@Service
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    // public static LocalDateTime now()

    @Override
    public EmpresaDTO save(EmpresaDTO empresaDTO) {
        log.debug("Request to save Empresa : {}", empresaDTO);
        Empresa empresa = empresaMapper.toEntity(empresaDTO);

        empresa = empresaRepository.save(empresa);
        return empresaMapper.toDto(empresa);
    }

    @Override
    public Optional<EmpresaDTO> partialUpdate(EmpresaDTO empresaDTO) {
        log.debug("Request to partially update Empresa : {}", empresaDTO);

        return empresaRepository
            .findById(empresaDTO.getId())
            .map(existingEmpresa -> {
                empresaMapper.partialUpdate(existingEmpresa, empresaDTO);

                return existingEmpresa;
            })
            .map(empresaRepository::save)
            .map(empresaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Empresas");
        return empresaRepository.findAll(pageable).map(empresaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpresaDTO> findOne(Long id) {
        log.debug("Request to get Empresa : {}", id);
        return empresaRepository.findById(id).map(empresaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empresa : {}", id);
        empresaRepository.deleteById(id);
    }
}
