package com.software.RouteFlex.repositories;

import com.software.RouteFlex.models.AsignarRuta;
import com.software.RouteFlex.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignarRutaRepository extends JpaRepository<AsignarRuta, Long> {

    @Query("SELECT ar FROM AsignarRuta ar WHERE ar.conductor.idConductor = :idConductor")
    List<AsignarRuta> findAsignarRutaByConductor(@Param("idConductor") Long idConductor);

    @Query("SELECT a FROM AsignarRuta a WHERE a.usuario.idUsuario = :idUsuario")
    List<AsignarRuta> findAsignarRutaByUsuario(@Param("idUsuario") Long idUsuario);

}
