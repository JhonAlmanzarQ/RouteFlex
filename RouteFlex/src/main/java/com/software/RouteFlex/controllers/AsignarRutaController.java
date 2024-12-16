package com.software.RouteFlex.controllers;

import com.software.RouteFlex.dto.AsignarRutaDto;
import com.software.RouteFlex.models.AsignarRuta;
import com.software.RouteFlex.services.AsignarRutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignar")
public class AsignarRutaController {

    @Autowired
    AsignarRutaService asignarRutaService;

    @GetMapping("listarUsuario/{id}")
    public List<AsignarRuta> listarAsignarUsuario(@PathVariable Long id) {
        return asignarRutaService.listarAsignarUsuario(id);
    }

    @GetMapping("listar/{id}")
    public List<AsignarRutaDto> listarAsignarRutaConductor(@PathVariable Long id) {
        return asignarRutaService.listarAsignarConductor(id);
    }

    @PostMapping("/crear")
    public AsignarRuta createRuta(@RequestBody AsignarRuta asignarRuta) {
        return asignarRutaService.crearAsignarRuta(asignarRuta);
    }

    @GetMapping("/listar")
    public List<AsignarRuta> listarRutas() {
        return asignarRutaService.listarAsignaciones();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Long id) {
        try {
            asignarRutaService.eliminarAsignacion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
