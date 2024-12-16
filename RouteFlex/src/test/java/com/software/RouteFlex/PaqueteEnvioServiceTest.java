package com.software.RouteFlex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.PaqueteEnvioRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.services.PaqueteEnvioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PaqueteEnvioServiceTest {

    @InjectMocks
    private PaqueteEnvioService paqueteEnvioService;

    @Mock
    private PaqueteEnvioRepository paqueteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPaqueteUsuario() {
        PaqueteEnvio paquete1 = new PaqueteEnvio();
        paquete1.setIdPaqueteEnvio(1L);
        PaqueteEnvio paquete2 = new PaqueteEnvio();
        paquete2.setIdPaqueteEnvio(2L);

        when(paqueteRepository.findPaqueteByUsuario(1L)).thenReturn(Arrays.asList(paquete1, paquete2));

        List<PaqueteEnvio> paquetes = paqueteEnvioService.listarpaqueteUsuario(1L);

        assertEquals(2, paquetes.size());
        verify(paqueteRepository, times(1)).findPaqueteByUsuario(1L);
    }

    @Test
    void testObtenerPaqueteEnvio_Existente() {
        PaqueteEnvio paquete = new PaqueteEnvio();
        paquete.setIdPaqueteEnvio(1L);
        when(paqueteRepository.findById(1L)).thenReturn(Optional.of(paquete));

        PaqueteEnvio result = paqueteEnvioService.obtenerPaqueteEnvio(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdPaqueteEnvio());
        verify(paqueteRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerPaqueteEnvio_NoExistente() {
        when(paqueteRepository.findById(1L)).thenReturn(Optional.empty());

        PaqueteEnvio result = paqueteEnvioService.obtenerPaqueteEnvio(1L);

        assertNull(result);
        verify(paqueteRepository, times(1)).findById(1L);
    }

    @Test
    void testCrearPaqueteEnvio_UsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        PaqueteEnvio paquete = new PaqueteEnvio();
        paquete.setUsuario(usuario);
        paquete.setNombre("Paquete 1");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(paqueteRepository.save(any(PaqueteEnvio.class))).thenReturn(paquete);

        PaqueteEnvio result = paqueteEnvioService.crearPaqueteEnvio(paquete);

        assertNotNull(result);
        assertEquals("Paquete 1", result.getNombre());
        verify(paqueteRepository, times(1)).save(paquete);
    }

    @Test
    void testCrearPaqueteEnvio_UsuarioNoExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        PaqueteEnvio paquete = new PaqueteEnvio();
        paquete.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            paqueteEnvioService.crearPaqueteEnvio(paquete);
        });

        verify(paqueteRepository, never()).save(any(PaqueteEnvio.class));
    }

    @Test
    void testActualizarPaqueteEnvio_Existente() {
        PaqueteEnvio paqueteExistente = new PaqueteEnvio();
        paqueteExistente.setIdPaqueteEnvio(1L);
        paqueteExistente.setNombre("Paquete Antiguo");

        PaqueteEnvio paqueteActualizado = new PaqueteEnvio();
        paqueteActualizado.setIdPaqueteEnvio(1L);
        paqueteActualizado.setNombre("Paquete Nuevo");

        when(paqueteRepository.findById(1L)).thenReturn(Optional.of(paqueteExistente));
        when(paqueteRepository.save(any(PaqueteEnvio.class))).thenReturn(paqueteActualizado);

        PaqueteEnvio result = paqueteEnvioService.actualizarUsuario(paqueteActualizado);

        assertNotNull(result);
        assertEquals("Paquete Nuevo", result.getNombre());
        verify(paqueteRepository, times(1)).save(any(PaqueteEnvio.class));
    }

    @Test
    void testActualizarPaqueteEnvio_NoExistente() {
        PaqueteEnvio paquete = new PaqueteEnvio();
        paquete.setIdPaqueteEnvio(1L);

        when(paqueteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            paqueteEnvioService.actualizarUsuario(paquete);
        });

        verify(paqueteRepository, never()).save(any(PaqueteEnvio.class));
    }

    @Test
    void testEliminarPaqueteEnvio_Existente() {
        when(paqueteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(paqueteRepository).deleteById(1L);

        assertDoesNotThrow(() -> paqueteEnvioService.eliminarPaqueteEnvio(1L));

        verify(paqueteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarPaqueteEnvio_NoExistente() {
        when(paqueteRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            paqueteEnvioService.eliminarPaqueteEnvio(1L);
        });

        verify(paqueteRepository, never()).deleteById(1L);
    }
}
