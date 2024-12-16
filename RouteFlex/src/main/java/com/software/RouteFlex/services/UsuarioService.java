package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public List<Usuario> listarUsuario() {
        return repository.findAll();
    }

    @Override
    public Usuario buscarUsuario(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioNuevo = repository.findById(usuario.getIdUsuario());
        if (usuarioNuevo.isPresent()){
            Usuario usuarioActualizar = usuarioNuevo.get();

            usuarioActualizar.setNombre(usuario.getNombre());
            usuarioActualizar.setApellido(usuario.getApellido());
            usuarioActualizar.setCorreo(usuario.getCorreo());
            usuarioActualizar.setContrasena(usuario.getContrasena());
            usuarioActualizar.setTelefono(usuario.getContrasena());

            return repository.save(usuarioActualizar);
        } else {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

    @Override
    public void eliminarUsuario(Long id) {

        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}
