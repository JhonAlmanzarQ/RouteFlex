package com.software.RouteFlex.services;

import com.software.RouteFlex.dto.AsignarRutaDto;
import com.software.RouteFlex.models.*;
import com.software.RouteFlex.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AsignarRutaService implements IAsignarRutaService {

    @Autowired
    AsignarRutaRepository asignarRutaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RutaRepository rutaRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    ConductorRepository conductorRepository;


    @Override
    public List<AsignarRuta> listarAsignarUsuario(Long idUsuario) {
        return asignarRutaRepository.findAsignarRutaByUsuario(idUsuario);
    }

    @Override
    public List<AsignarRutaDto> listarAsignarConductor(Long idConductor) {
        List<AsignarRuta> asignaciones = asignarRutaRepository.findAsignarRutaByConductor(idConductor);

        return asignaciones.stream().map(asignarRuta -> {
            // Mapeo de la RutaDTO
            AsignarRutaDto.RutaDTO rutaDTO = new AsignarRutaDto.RutaDTO(
                    asignarRuta.getRuta().getOverviewPolyline(),
                    asignarRuta.getRuta().getCoordenadas()
            );

            // Mapeo de la VehiculoDTO
            AsignarRutaDto.VehiculoDTO vehiculoDTO = new AsignarRutaDto.VehiculoDTO(
                    asignarRuta.getVehiculo().getPeso().intValue(),
                    asignarRuta.getVehiculo().getPlaca(),
                    asignarRuta.getVehiculo().isEstado(),
                    asignarRuta.getVehiculo().getMarca(),
                    asignarRuta.getVehiculo().getIdVehiculo(),
                    asignarRuta.getVehiculo().getTipoVehiculo()
            );

            // Retornar el DTO completo con el ID de la asignación de ruta
            return new AsignarRutaDto(
                    asignarRuta.getIdAsignarRuta(),  // Agregar el ID de la asignación
                    rutaDTO,
                    vehiculoDTO
            );
        }).collect(Collectors.toList());
    }


    @Override
    public AsignarRuta crearAsignarRuta(AsignarRuta asignarRuta) {
        Usuario usuario = usuarioRepository.findById(asignarRuta.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Ruta ruta = rutaRepository.findById(asignarRuta.getRuta().getIdRuta())
                .orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada"));
        Vehiculo vehiculo = vehiculoRepository.findById(asignarRuta.getVehiculo().getIdVehiculo())
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));
        Conductor conductor = conductorRepository.findById(asignarRuta.getConductor().getIdConductor())
                .orElseThrow(() -> new EntityNotFoundException("Conductor no encontrado"));

        // Asignar las entidades validadas
        asignarRuta.setUsuario(usuario);
        asignarRuta.setRuta(ruta);
        asignarRuta.setVehiculo(vehiculo);
        asignarRuta.setConductor(conductor);

        // Guardar la asignación
        return asignarRutaRepository.save(asignarRuta);
    }

    @Override
    public List<AsignarRuta> listarAsignaciones() {
        return asignarRutaRepository.findAll();
    }

    @Override
    public void eliminarAsignacion(Long idAsignarRuta) {
        if (!asignarRutaRepository.existsById(idAsignarRuta)) {
            throw new EntityNotFoundException("Asignación de ruta no encontrada");
        }
        asignarRutaRepository.deleteById(idAsignarRuta);
    }
}
