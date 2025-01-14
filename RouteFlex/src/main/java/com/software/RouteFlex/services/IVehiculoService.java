package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Vehiculo;

import java.util.List;

public interface IVehiculoService {

    //Listar por id
    List<Vehiculo> listarVehiculosUsuario(Long idUsuario);

    //Listar
    List<Vehiculo> listarVehiculo();

    //Obtener
    Vehiculo buscarVehiculo(Long id);

    //Crear
    Vehiculo crearVehiculo(Vehiculo vehiculo);

    //Actualizar
    Vehiculo actualizarVehiculo(Vehiculo vehiculo);

    //Eliminar
    void eliminarVehiculo(Long id);

}
