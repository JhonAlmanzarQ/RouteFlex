package com.software.RouteFlex.controllers;

import com.software.RouteFlex.dto.LoginRequest;
import com.software.RouteFlex.models.Conductor;
import com.software.RouteFlex.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conductor")
public class ConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping("/listar/{id}")
    public List<Conductor> listarConductorUsuario(@PathVariable Long id) {
        return conductorService.lsitarConcudtorUsuario(id);
    }

    @GetMapping("/listar")
    public List<Conductor> listar() {
        return conductorService.listarConductor();
    }

    @GetMapping("/obtener/{id}")
    public Conductor obtener(@PathVariable Long id) {
        return conductorService.obtenerConductor(id);
    }

    @PostMapping("/crear")
    public Conductor crear(@RequestBody Conductor conductor) {
        return conductorService.crearConductor(conductor);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestBody Conductor conductor) {
        try {
            conductorService.actualizarConductor(conductor);
            return ResponseEntity.ok(conductor);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            conductorService.eliminarConductor(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Conductor> validarConductor(@RequestBody LoginRequest loginRequest) {
        try {
            Conductor conductor = conductorService.validarConductor(loginRequest.getNombre(), loginRequest.getContrasena());
            return ResponseEntity.ok(conductor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
