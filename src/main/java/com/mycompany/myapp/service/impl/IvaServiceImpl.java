package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Iva;
import com.mycompany.myapp.repository.IvaRepository;
import com.mycompany.myapp.service.IvaService;
import com.mycompany.myapp.service.dto.IvaDTO;
import com.mycompany.myapp.service.mapper.IvaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Iva}.
 */
@Service
@Transactional
public class IvaServiceImpl implements IvaService {

    private final Logger log = LoggerFactory.getLogger(IvaServiceImpl.class);

    private final IvaRepository ivaRepository;

    private final IvaMapper ivaMapper;

    public IvaServiceImpl(IvaRepository ivaRepository, IvaMapper ivaMapper) {
        this.ivaRepository = ivaRepository;
        this.ivaMapper = ivaMapper;
    }

    @Override
    public IvaDTO save(IvaDTO ivaDTO) {
        log.debug("Request to save Iva : {}", ivaDTO);
        Iva iva = ivaMapper.toEntity(ivaDTO);
        iva = ivaRepository.save(iva);
        return ivaMapper.toDto(iva);
    }

    @Override
    public Optional<IvaDTO> partialUpdate(IvaDTO ivaDTO) {
        log.debug("Request to partially update Iva : {}", ivaDTO);

        return ivaRepository
            .findById(ivaDTO.getId())
            .map(existingIva -> {
                ivaMapper.partialUpdate(existingIva, ivaDTO);

                return existingIva;
            })
            .map(ivaRepository::save)
            .map(ivaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IvaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ivas");
        return ivaRepository.findAll(pageable).map(ivaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IvaDTO> findOne(Long id) {
        log.debug("Request to get Iva : {}", id);
        return ivaRepository.findById(id).map(ivaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Iva : {}", id);
        ivaRepository.deleteById(id);
    }
}
