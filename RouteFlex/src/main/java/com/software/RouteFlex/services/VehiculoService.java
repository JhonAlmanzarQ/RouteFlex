package com.software.RouteFlex.services;

import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.models.Vehiculo;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.repositories.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehiculoService implements IVehiculoService{

    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Vehiculo> listarVehiculosUsuario(Long idUsuario) {
        return vehiculoRepository.findVehiculosByUsuario(idUsuario);
    }

    @Override
    public List<Vehiculo> listarVehiculo() {
        return vehiculoRepository.findAll();
    }


    @Override
    public Vehiculo buscarVehiculo(Long id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        Usuario usuario = usuarioRepository.findById(vehiculo.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        vehiculo.setUsuario(usuario);

        return vehiculoRepository.save(vehiculo);
    }


    @Override
    public Vehiculo actualizarVehiculo(Vehiculo vehiculo) {
        Optional<Vehiculo> vehiculoExistente = vehiculoRepository.findById(vehiculo.getIdVehiculo());
                if(vehiculoExistente.isPresent()){
                    Vehiculo vehiculoActualizar = vehiculoExistente.get();

                    vehiculoActualizar.setTipoVehiculo(vehiculo.getTipoVehiculo());
                    vehiculoActualizar.setPeso(vehiculo.getPeso());
                    vehiculoActualizar.setMarca(vehiculo.getMarca());
                    vehiculoActualizar.setPlaca(vehiculo.getPlaca());
                    vehiculoActualizar.setEstado(vehiculo.isEstado());

                    return vehiculoRepository.save(vehiculoActualizar);
                } else {
                    throw new IllegalArgumentException("Vehículo no encontrado");
                }
    }

    @Override
    public void eliminarVehiculo(Long id) {
        if(vehiculoRepository.existsById(id)){
            vehiculoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Vehículo con ID " + id + " no encontrado.");
        }
    }
}
