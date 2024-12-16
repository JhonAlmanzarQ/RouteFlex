package com.software.RouteFlex.repositories;
import com.software.RouteFlex.models.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    Conductor findByNombre(String nombre);

    @Query("SELECT c FROM Conductor c WHERE c.usuario.idUsuario = :idUsuario")
    List<Conductor> findConductorByUsuario(@Param("idUsuario") Long idUsuario);

}
