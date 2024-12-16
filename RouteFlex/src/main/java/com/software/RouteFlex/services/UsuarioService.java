package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuario() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        try {
            // Intentamos guardar al usuario
            return usuarioRepository.save(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioNuevo = usuarioRepository.findById(usuario.getIdUsuario());
        if (usuarioNuevo.isPresent()){
            Usuario usuarioActualizar = usuarioNuevo.get();

            usuarioActualizar.setNombre(usuario.getNombre());
            usuarioActualizar.setApellido(usuario.getApellido());
            usuarioActualizar.setCorreo(usuario.getCorreo());
            usuarioActualizar.setContrasena(usuario.getContrasena());
            usuarioActualizar.setTelefono(usuario.getContrasena());

            return usuarioRepository.save(usuarioActualizar);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    @Override
    public void eliminarUsuario(Long id) {

        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    @Override
    public Usuario validarUsuario(String nombre, String contrasena) {
        Usuario usuario = usuarioRepository.findByNombre(nombre);

        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        return usuario;
    }

}
