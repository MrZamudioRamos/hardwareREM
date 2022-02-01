package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.IvaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Iva}.
 */
public interface IvaService {
    /**
     * Save a iva.
     *
     * @param ivaDTO the entity to save.
     * @return the persisted entity.
     */
    IvaDTO save(IvaDTO ivaDTO);

    /**
     * Partially updates a iva.
     *
     * @param ivaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IvaDTO> partialUpdate(IvaDTO ivaDTO);

    /**
     * Get all the ivas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IvaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" iva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IvaDTO> findOne(Long id);

    /**
     * Delete the "id" iva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
