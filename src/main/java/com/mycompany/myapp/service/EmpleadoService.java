package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EmpleadoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Empleado}.
 */
public interface EmpleadoService {
    /**
     * Save a empleado.
     *
     * @param empleadoDTO the entity to save.
     * @return the persisted entity.
     */
    EmpleadoDTO save(EmpleadoDTO empleadoDTO);

    /**
     * Partially updates a empleado.
     *
     * @param empleadoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmpleadoDTO> partialUpdate(EmpleadoDTO empleadoDTO);

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmpleadoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" empleado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpleadoDTO> findOne(Long id);

    /**
     * Delete the "id" empleado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
