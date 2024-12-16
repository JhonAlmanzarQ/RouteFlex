package com.software.RouteFlex.services;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Ruta;

import java.util.List;

public interface IRutaService {

    //Listar ruta por usuario
    List<Ruta> listarRutaUsuario(Long id);

    //Crear ruta
    Ruta crearRuta(List<PaqueteEnvio> paqueteEnvio);

    //Listar
    List<Ruta> listarRutas();

    //Eliminar
    void eliminarRuta(Long id);

}
