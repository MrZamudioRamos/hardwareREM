package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AlmacenDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Almacen}.
 */
public interface AlmacenService {
    /**
     * Save a almacen.
     *
     * @param almacenDTO the entity to save.
     * @return the persisted entity.
     */
    AlmacenDTO save(AlmacenDTO almacenDTO);

    /**
     * Partially updates a almacen.
     *
     * @param almacenDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlmacenDTO> partialUpdate(AlmacenDTO almacenDTO);

    /**
     * Get all the almacens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlmacenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" almacen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlmacenDTO> findOne(Long id);

    /**
     * Delete the "id" almacen.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
