package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pedido;
import com.mycompany.myapp.domain.Producto;
import com.mycompany.myapp.service.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    @Query("SELECT u FROM Producto u WHERE u.pedido = ?1")
    List<Producto> findAllByPedidoId(Pedido pedido);
}
