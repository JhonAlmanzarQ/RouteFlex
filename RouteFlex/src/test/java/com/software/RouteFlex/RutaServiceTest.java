package com.software.RouteFlex;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Ruta;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.RutaRepository;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.services.RutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RutaServiceTest {

    @InjectMocks
    private RutaService rutaService;

    @Mock
    private RutaRepository rutaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarRutaUsuario() {
        Long idUsuario = 1L;
        Ruta ruta = new Ruta();
        ruta.setIdRuta(1L);
        ruta.setUsuario(new Usuario());

        when(rutaRepository.findRutaByUsuario(idUsuario)).thenReturn(Arrays.asList(ruta));

        List<Ruta> rutas = rutaService.listarRutaUsuario(idUsuario);

        assertNotNull(rutas);
        assertEquals(1, rutas.size());
        verify(rutaRepository, times(1)).findRutaByUsuario(idUsuario);
    }

    @Test
    public void testEliminarRuta() {
        Long idRuta = 1L;
        when(rutaRepository.existsById(idRuta)).thenReturn(true);

        rutaService.eliminarRuta(idRuta);

        verify(rutaRepository, times(1)).deleteById(idRuta);
    }

    @Test
    public void testEliminarRutaNotFound() {
        Long idRuta = 1L;
        when(rutaRepository.existsById(idRuta)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rutaService.eliminarRuta(idRuta);
        });

        assertEquals("Ruta no encontrada", exception.getMessage());
        verify(rutaRepository, never()).deleteById(idRuta);
    }
}
