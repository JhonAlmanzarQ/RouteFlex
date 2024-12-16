package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Conductor;

import java.util.List;

public interface IConductorService {

    //Listar
    List<Conductor> listarConductor();

    //Obtener
    Conductor obtenerConductor(Long id);

    //Crear
    Conductor crearConductor(Conductor conductor);

    //Actualizar
    Conductor actualizarConductor(Conductor conductor);

    //Eliminar
    void eliminarConductor(Long id);
}
