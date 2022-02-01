package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EmpresaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Empresa}.
 */
public interface EmpresaService {
    /**
     * Save a empresa.
     *
     * @param empresaDTO the entity to save.
     * @return the persisted entity.
     */
    EmpresaDTO save(EmpresaDTO empresaDTO);

    /**
     * Partially updates a empresa.
     *
     * @param empresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmpresaDTO> partialUpdate(EmpresaDTO empresaDTO);

    /**
     * Get all the empresas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmpresaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" empresa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpresaDTO> findOne(Long id);

    /**
     * Delete the "id" empresa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
