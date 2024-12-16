package com.software.RouteFlex.controllers;

import com.software.RouteFlex.models.PaqueteEnvio;
import com.software.RouteFlex.services.PaqueteEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paquete")
public class PaqueteEnvioController {

    @Autowired
    private PaqueteEnvioService paqueteEnvioService;

    @GetMapping("/listar")
    public List<PaqueteEnvio> listar(){
        return paqueteEnvioService.listarPaqueteEnvio();
    }

    @GetMapping("/listar/{id}")
    public PaqueteEnvio obtener(@PathVariable Long id){
        return paqueteEnvioService.obtenerPaqueteEnvio(id);
    }

    @PostMapping("/crear")
    public PaqueteEnvio crear(@RequestBody PaqueteEnvio paqueteEnvio){
        return paqueteEnvioService.crearPaqueteEnvio(paqueteEnvio);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestBody PaqueteEnvio paqueteEnvioActualizado){
        try {
            PaqueteEnvio paqueteEnvio = paqueteEnvioService.actualizarUsuario(paqueteEnvioActualizado);
            return ResponseEntity.ok(paqueteEnvio);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            paqueteEnvioService.eliminarPaqueteEnvio(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
