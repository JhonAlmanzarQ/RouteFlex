package com.software.RouteFlex.controllers;

import com.software.RouteFlex.models.Vehiculo;
import com.software.RouteFlex.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
@CrossOrigin("http://localhost:4200/")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/listar/{idUsuario}")
    public List<Vehiculo> listarPorUsuario(@PathVariable Long idUsuario) {
        return vehiculoService.listarVehiculosUsuario(idUsuario);
    }

    @GetMapping("/listar")
    public List<Vehiculo> listar(){
        return vehiculoService.listarVehiculo();
    }

    @GetMapping("/buscar/{id}")
    public Vehiculo buscar(@PathVariable Long id){
        return vehiculoService.buscarVehiculo(id);
    }

    @PostMapping("/crear")
    public Vehiculo crear(@RequestBody Vehiculo vehiculo){
        return vehiculoService.crearVehiculo(vehiculo);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarVehiculo(@RequestBody Vehiculo vehiculoActualizado){

        try {
            Vehiculo vehiculo = vehiculoService.actualizarVehiculo(vehiculoActualizado);
            return ResponseEntity.ok(vehiculo);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            vehiculoService.eliminarVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}




