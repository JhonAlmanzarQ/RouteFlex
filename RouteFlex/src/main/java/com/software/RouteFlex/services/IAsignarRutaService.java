package com.software.RouteFlex.services;

import com.software.RouteFlex.dto.AsignarRutaDto;
import com.software.RouteFlex.models.AsignarRuta;

import java.util.List;

public interface IAsignarRutaService {

    //Listar por id conductor
    List<AsignarRuta> listarAsignarUsuario(Long idUsuario);

    //Listar por id
    List<AsignarRutaDto> listarAsignarConductor(Long idConductor);

    //Crear
    AsignarRuta crearAsignarRuta(AsignarRuta asignarRuta);

    //Listar
    List<AsignarRuta> listarAsignaciones();

    //Eliminar
    void eliminarAsignacion(Long idAsignarRuta);
}
