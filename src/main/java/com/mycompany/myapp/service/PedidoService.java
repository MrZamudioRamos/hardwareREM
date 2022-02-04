package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.service.dto.PedidoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Pedido}.
 */
public interface PedidoService {
    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save.
     * @return the persisted entity.
     */
    PedidoDTO save(PedidoDTO pedidoDTO);

    /**
     * Partially updates a pedido.
     *
     * @param pedidoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PedidoDTO> partialUpdate(PedidoDTO pedidoDTO);

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PedidoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidoDTO> findOne(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<PedidoDTO> findAllBySearchingParam(String filtro, Pageable pageable);

    List<PedidoDTO> findAllByEmpleado(Empleado empleado);
}
