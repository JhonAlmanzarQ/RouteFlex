package com.software.RouteFlex.services;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.PaqueteEnvioRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PaqueteEnvioService implements IPaqueteEnvioService{

    @Autowired
    PaqueteEnvioRepository paqueteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<PaqueteEnvio> listarpaqueteUsuario(Long id) {
        return paqueteRepository.findPaqueteByUsuario(id);
    }

    @Override
    public List<PaqueteEnvio> listarPaqueteEnvio() {
        return paqueteRepository.findAll();
    }

    @Override
    public PaqueteEnvio obtenerPaqueteEnvio(Long id) {
        return paqueteRepository.findById(id).orElse(null);
    }

    @Override
    public PaqueteEnvio crearPaqueteEnvio(PaqueteEnvio paqueteEnvio) {

        Usuario usuario = usuarioRepository.findById(paqueteEnvio.getUsuario().getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        paqueteEnvio.setUsuario(usuario);
        return paqueteRepository.save(paqueteEnvio);
    }

    @Override
    public PaqueteEnvio actualizarUsuario(PaqueteEnvio paqueteEnvio) {
        Optional<PaqueteEnvio> paqueteEnvioExistente = paqueteRepository.findById(paqueteEnvio.getIdPaqueteEnvio());

        if (paqueteEnvioExistente.isPresent()) {
            PaqueteEnvio paqueteEnvioActualizar = paqueteEnvioExistente.get();

            paqueteEnvioActualizar.setNombre(paqueteEnvio.getNombre());
            paqueteEnvioActualizar.setNumero(paqueteEnvio.getNumero());
            paqueteEnvioActualizar.setDireccion(paqueteEnvio.getDireccion());
            paqueteEnvioActualizar.setPeso(paqueteEnvio.getPeso());
            paqueteEnvioActualizar.setFecha(paqueteEnvio.getFecha());

            return paqueteRepository.save(paqueteEnvioActualizar);
        } else {
            throw new IllegalArgumentException("Paquete envio no encontrado");
        }

    }

    @Override
    public void eliminarPaqueteEnvio(Long id) {
        if (paqueteRepository.existsById(id)){
            paqueteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Paquete envio no encontrado");
        }
    }

}
