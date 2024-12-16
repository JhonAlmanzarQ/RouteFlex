package com.software.RouteFlex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.software.RouteFlex.models.Conductor;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.ConductorRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.services.ConductorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

class ConductorServiceTest {

    @InjectMocks
    private ConductorService conductorService;

    @Mock
    private ConductorRepository conductorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarConductor() {
        Conductor conductor1 = new Conductor();
        conductor1.setNombre("Juan");
        Conductor conductor2 = new Conductor();
        conductor2.setNombre("Maria");

        when(conductorRepository.findAll()).thenReturn(Arrays.asList(conductor1, conductor2));

        List<Conductor> conductores = conductorService.listarConductor();

        assertEquals(2, conductores.size());
        assertEquals("Juan", conductores.get(0).getNombre());
        verify(conductorRepository, times(1)).findAll();
    }

    @Test
    void testObtenerConductor_Existente() {
        Conductor conductor = new Conductor();
        conductor.setIdConductor(1L);
        when(conductorRepository.findById(1L)).thenReturn(Optional.of(conductor));

        Conductor result = conductorService.obtenerConductor(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdConductor());
        verify(conductorRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerConductor_NoExistente() {
        when(conductorRepository.findById(1L)).thenReturn(Optional.empty());

        Conductor result = conductorService.obtenerConductor(1L);

        assertNull(result);
        verify(conductorRepository, times(1)).findById(1L);
    }

    @Test
    void testCrearConductor_UsuarioExistente() {
        Conductor conductor = new Conductor();
        conductor.setNombre("NuevoConductor");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        conductor.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(conductorRepository.save(any(Conductor.class))).thenReturn(conductor);

        Conductor result = conductorService.crearConductor(conductor);

        assertNotNull(result);
        assertEquals("NuevoConductor", result.getNombre());
        verify(conductorRepository, times(1)).save(conductor);
    }

    @Test
    void testCrearConductor_UsuarioNoExistente() {
        Conductor conductor = new Conductor();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        conductor.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            conductorService.crearConductor(conductor);
        });

        verify(conductorRepository, never()).save(any(Conductor.class));
    }

    @Test
    void testEliminarConductor_Existente() {
        when(conductorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(conductorRepository).deleteById(1L);

        assertDoesNotThrow(() -> conductorService.eliminarConductor(1L));

        verify(conductorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarConductor_NoExistente() {
        when(conductorRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            conductorService.eliminarConductor(1L);
        });

        verify(conductorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testValidarConductor_Valido() {
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan");
        conductor.setContrasena("password");

        when(conductorRepository.findByNombre("Juan")).thenReturn(conductor);

        Conductor result = conductorService.validarConductor("Juan", "password");

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(conductorRepository, times(1)).findByNombre("Juan");
    }

    @Test
    void testValidarConductor_Invalido() {
        when(conductorRepository.findByNombre("Juan")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            conductorService.validarConductor("Juan", "password");
        });

        verify(conductorRepository, times(1)).findByNombre("Juan");
    }
}
