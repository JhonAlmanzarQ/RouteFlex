package com.software.RouteFlex.controllers;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.models.Ruta;
import com.software.RouteFlex.services.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ruta")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @GetMapping("listar/{id}")
    public List<Ruta> listarRuta(@PathVariable Long id) {
        return rutaService.listarRutaUsuario(id);
    }

    // Endpoint para crear una nueva ruta
    @PostMapping("/crear")
    public ResponseEntity<Ruta> crearRuta(@RequestBody List<PaqueteEnvio> paqueteEnvio) {
        try {
            Ruta rutaCreada = rutaService.crearRuta(paqueteEnvio);
            return new ResponseEntity<>(rutaCreada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public List<Ruta> listarRuta() {
        return rutaService.listarRutas();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Long id) {
        try {
            rutaService.eliminarRuta(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
