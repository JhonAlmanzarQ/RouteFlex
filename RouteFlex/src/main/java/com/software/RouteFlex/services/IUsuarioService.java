package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Usuario;

import java.util.List;

public interface IUsuarioService {

    //Listar usuarios
    List<Usuario> listarUsuario();

    //Buscar usuario por id
    Usuario buscarUsuario(Long id);

    //Crear usuario
    Usuario crearUsuario(Usuario usuario);

    //Actualizar usuario
    Usuario actualizarUsuario(Usuario usuario);

    //Eliminar usario
    void eliminarUsuario(Long id);

    //Loggin
    Usuario validarUsuario(String nombre, String contrasena);

}
