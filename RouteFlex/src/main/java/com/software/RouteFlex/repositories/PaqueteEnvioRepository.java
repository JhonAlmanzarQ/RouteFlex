package com.software.RouteFlex.repositories;

import com.software.RouteFlex.models.PaqueteEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaqueteEnvioRepository extends JpaRepository<PaqueteEnvio, Long> {

    @Query("SELECT p FROM PaqueteEnvio p WHERE p.usuario.idUsuario = :idUsuario")
    List<PaqueteEnvio> findPaqueteByUsuario(@Param("idUsuario") Long idUsuario);

}
