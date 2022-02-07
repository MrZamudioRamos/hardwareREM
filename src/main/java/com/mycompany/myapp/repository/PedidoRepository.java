package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Empleado;
import com.mycompany.myapp.domain.Pedido;
import java.util.Collection;
import java.util.List;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
    @Query("SELECT u FROM Pedido u WHERE u.empleado = ?1")
    List<Pedido> findAllByEmpleado(Empleado empleado);

    // @Query("")
}
