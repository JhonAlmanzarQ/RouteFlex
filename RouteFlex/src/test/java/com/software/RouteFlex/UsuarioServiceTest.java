package com.software.RouteFlex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.software.RouteFlex.models.Usuario;
import com.software.RouteFlex.repositories.UsuarioRepository;
import com.software.RouteFlex.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarUsuario_ShouldReturnListOfUsuarios() {
        // Arrange
        Usuario usuario1 = new Usuario(1L, "John", "Doe", "john.doe@example.com", "password", "123456789");
        Usuario usuario2 = new Usuario(2L, "Jane", "Doe", "jane.doe@example.com", "password", "987654321");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        // Act
        List<Usuario> usuarios = usuarioService.listarUsuario();

        // Assert
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test
    void buscarUsuario_ShouldReturnUsuarioWhenExists() {
        // Arrange
        Usuario usuario = new Usuario(1L, "John", "Doe", "john.doe@example.com", "password", "123456789");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        Usuario result = usuarioService.buscarUsuario(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNombre());
    }

    @Test
    void buscarUsuario_ShouldReturnNullWhenNotExists() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Usuario result = usuarioService.buscarUsuario(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void crearUsuario_ShouldSaveAndReturnUsuario() {
        // Arrange
        Usuario usuario = new Usuario(null, "John", "Doe", "john.doe@example.com", "password", "123456789");
        when(usuarioRepository.save(usuario)).thenReturn(new Usuario(1L, "John", "Doe", "john.doe@example.com", "password", "123456789"));

        // Act
        Usuario result = usuarioService.crearUsuario(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdUsuario());
    }

    @Test
    void eliminarUsuario_ShouldCallDeleteWhenUsuarioExists() {
        // Arrange
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        // Act
        usuarioService.eliminarUsuario(1L);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void validarUsuario_ShouldReturnUsuarioWhenCredentialsMatch() {
        // Arrange
        Usuario usuario = new Usuario(1L, "John", "Doe", "john.doe@example.com", "password", "123456789");
        when(usuarioRepository.findByNombre("John")).thenReturn(usuario);

        // Act
        Usuario result = usuarioService.validarUsuario("John", "password");

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNombre());
    }

    @Test
    void validarUsuario_ShouldThrowExceptionWhenCredentialsDoNotMatch() {
        // Arrange
        Usuario usuario = new Usuario(1L, "John", "Doe", "john.doe@example.com", "wrongPassword", "123456789");
        when(usuarioRepository.findByNombre("John")).thenReturn(usuario);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.validarUsuario("John", "password"));
    }
}
