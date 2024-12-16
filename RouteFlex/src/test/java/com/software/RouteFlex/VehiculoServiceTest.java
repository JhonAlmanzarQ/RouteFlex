package com.software.RouteFlex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.models.Vehiculo;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.repositories.VehiculoRepository;
import com.software.RouteFlex.services.VehiculoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class VehiculoServiceTest {

    @InjectMocks
    private VehiculoService vehiculoService;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarVehiculosUsuario() {
        Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setIdVehiculo(1L);
        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setIdVehiculo(2L);

        when(vehiculoRepository.findVehiculosByUsuario(1L)).thenReturn(Arrays.asList(vehiculo1, vehiculo2));

        List<Vehiculo> vehiculos = vehiculoService.listarVehiculosUsuario(1L);

        assertEquals(2, vehiculos.size());
        verify(vehiculoRepository, times(1)).findVehiculosByUsuario(1L);
    }

    @Test
    void testBuscarVehiculo_Existente() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        Vehiculo result = vehiculoService.buscarVehiculo(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdVehiculo());
        verify(vehiculoRepository, times(1)).findById(1L);
    }

    @Test
    void testBuscarVehiculo_NoExistente() {
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Vehiculo result = vehiculoService.buscarVehiculo(1L);

        assertNull(result);
        verify(vehiculoRepository, times(1)).findById(1L);
    }

    @Test
    void testCrearVehiculo_UsuarioExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setUsuario(usuario);
        vehiculo.setTipoVehiculo("Camión");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo result = vehiculoService.crearVehiculo(vehiculo);

        assertNotNull(result);
        assertEquals("Camión", result.getTipoVehiculo());
        verify(vehiculoRepository, times(1)).save(vehiculo);
    }

    @Test
    void testCrearVehiculo_UsuarioNoExistente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setUsuario(usuario);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            vehiculoService.crearVehiculo(vehiculo);
        });

        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void testActualizarVehiculo_Existente() {
        Vehiculo vehiculoExistente = new Vehiculo();
        vehiculoExistente.setIdVehiculo(1L);
        vehiculoExistente.setTipoVehiculo("Camión");

        Vehiculo vehiculoActualizado = new Vehiculo();
        vehiculoActualizado.setIdVehiculo(1L);
        vehiculoActualizado.setTipoVehiculo("Furgoneta");

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculoExistente));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoActualizado);

        Vehiculo result = vehiculoService.actualizarVehiculo(vehiculoActualizado);

        assertNotNull(result);
        assertEquals("Furgoneta", result.getTipoVehiculo());
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void testActualizarVehiculo_NoExistente() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(1L);

        when(vehiculoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.actualizarVehiculo(vehiculo);
        });

        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void testEliminarVehiculo_Existente() {
        when(vehiculoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vehiculoRepository).deleteById(1L);

        assertDoesNotThrow(() -> vehiculoService.eliminarVehiculo(1L));

        verify(vehiculoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarVehiculo_NoExistente() {
        when(vehiculoRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            vehiculoService.eliminarVehiculo(1L);
        });

        verify(vehiculoRepository, never()).deleteById(1L);
    }
}
