package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Almacen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Almacen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {}
