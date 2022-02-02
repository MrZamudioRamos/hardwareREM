package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ComponenteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Componente}.
 */
public interface ComponenteService {
    /**
     * Save a componente.
     *
     * @param componenteDTO the entity to save.
     * @return the persisted entity.
     */
    ComponenteDTO save(ComponenteDTO componenteDTO);

    /**
     * Partially updates a componente.
     *
     * @param componenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComponenteDTO> partialUpdate(ComponenteDTO componenteDTO);

    /**
     * Get all the componentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComponenteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" componente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComponenteDTO> findOne(Long id);

    /**
     * Delete the "id" componente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<ComponenteDTO> findAllBySearchingParam(String filtro, Pageable pageable);
}
