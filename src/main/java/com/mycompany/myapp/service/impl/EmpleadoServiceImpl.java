package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.repository.EmpleadoRepository;
import com.mycompany.myapp.repository.specification.EmpleadoSpecification;
import com.mycompany.myapp.service.EmpleadoService;
import com.mycompany.myapp.service.dto.EmpleadoDTO;
import com.mycompany.myapp.service.mapper.EmpleadoMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Empleado}.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final Logger log = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    private final EmpleadoRepository empleadoRepository;

    private final EmpleadoMapper empleadoMapper;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
    }

    @Override
    public EmpleadoDTO save(EmpleadoDTO empleadoDTO) {
        log.debug("Request to save Empleado : {}", empleadoDTO);
        Empleado empleado = empleadoMapper.toEntity(empleadoDTO);

        Set<Pedido> pedidosEmpleado = empleado.getPedidos();
        empleado.setNumeroPedidos(pedidosEmpleado.size());

        empleado = empleadoRepository.save(empleado);
        return empleadoMapper.toDto(empleado);
    }

    @Override
    public Optional<EmpleadoDTO> partialUpdate(EmpleadoDTO empleadoDTO) {
        log.debug("Request to partially update Empleado : {}", empleadoDTO);

        return empleadoRepository
            .findById(empleadoDTO.getId())
            .map(existingEmpleado -> {
                empleadoMapper.partialUpdate(existingEmpleado, empleadoDTO);

                return existingEmpleado;
            })
            .map(empleadoRepository::save)
            .map(empleadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpleadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Empleados");
        return empleadoRepository.findAll(pageable).map(empleadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadoDTO> findOne(Long id) {
        log.debug("Request to get Empleado : {}", id);
        return empleadoRepository.findById(id).map(empleadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empleado : {}", id);
        empleadoRepository.deleteById(id);
    }

    @Override
    public Page<EmpleadoDTO> findAllBySearchingParam(String filtro, Pageable pageable) {
        log.debug("Filtro");
        return empleadoRepository.findAll(EmpleadoSpecification.searchingParam(filtro), pageable).map(empleadoMapper::toDto);
    }
}
