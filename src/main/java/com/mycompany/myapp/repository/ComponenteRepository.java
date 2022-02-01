package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Componente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Componente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComponenteRepository extends JpaRepository<Componente, Long> {}
