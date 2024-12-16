package com.software.RouteFlex.repositories;

import com.software.RouteFlex.models.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    @Query("SELECT r FROM Ruta r WHERE r.usuario.idUsuario = :idUsuario")
    List<Ruta> findRutaByUsuario(@Param("idUsuario") Long idUsuario);

}
