package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Empleado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Empleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>, JpaSpecificationExecutor<Empleado> {

    @Query("SELECT e FROM Empleado e JOIN e.user u WHERE u.login = ?1")
    Empleado findEmpleadoByLogin(String login);
}
