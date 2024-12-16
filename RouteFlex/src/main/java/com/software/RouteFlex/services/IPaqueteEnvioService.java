package com.software.RouteFlex.services;

import com.software.RouteFlex.models.PaqueteEnvio;

import java.util.List;

public interface IPaqueteEnvioService {

    //Listar
    List<PaqueteEnvio> listarPaqueteEnvio();

    //Obtener
    PaqueteEnvio obtenerPaqueteEnvio(Long id);

    //Crear
    PaqueteEnvio crearPaqueteEnvio(PaqueteEnvio paqueteEnvio);

    //Actualizar
    PaqueteEnvio actualizarUsuario(PaqueteEnvio paqueteEnvio);

    //Eliminar
    void eliminarPaqueteEnvio(Long id);

}
