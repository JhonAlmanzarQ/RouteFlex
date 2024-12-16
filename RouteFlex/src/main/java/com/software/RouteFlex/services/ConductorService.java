package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Conductor;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.ConductorRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConductorService implements IConductorService{

    @Autowired
    ConductorRepository conductorRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Conductor> listarConductor() {
        return conductorRepository.findAll();
    }

    @Override
    public Conductor obtenerConductor(Long id) {
        return conductorRepository.findById(id).orElse(null);
    }

    @Override
    public Conductor crearConductor(Conductor conductor) {
        Usuario usuario = usuarioRepository.findById(conductor.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        conductor.setUsuario(usuario);
        return conductorRepository.save(conductor);
    }

    @Override
    public Conductor actualizarConductor(Conductor conductor) {

        Optional<Conductor> conductorExistente = conductorRepository.findById(conductor.getIdConductor());
        if (conductorExistente.isPresent()) {
            Conductor conductorActualizar = conductorExistente.get();

            conductorActualizar.setNombre(conductor.getNombre());
            conductorActualizar.setApellido(conductor.getApellido());
            conductorActualizar.setCorreo(conductor.getCorreo());
            conductorActualizar.setTelefono(conductorActualizar.getTelefono());
            conductorActualizar.setContrasena(conductor.getContrasena());

            return conductorRepository.save(conductorActualizar);
        } else {
            throw new IllegalArgumentException("Conductor no encontrado");
        }
    }

    @Override
    public void eliminarConductor(Long id) {
        if (conductorRepository.existsById(id)) {
            conductorRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Conductor no encontrado");
        }
    }
}
