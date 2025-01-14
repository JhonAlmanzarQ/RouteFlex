package com.software.RouteFlex.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignarRutaDto {

    private Long idAsignarRuta;
    private RutaDTO ruta;
    private VehiculoDTO vehiculo;
    private ConductorDTO conductor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RutaDTO {
        private String overviewPolyline;
        private List<String> coordenadas;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehiculoDTO {
        private String placa;
        private boolean estado;
        private String marca;
        private Long idVehiculo;
        private String tipoVehiculo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConductorDTO {
        private Long idConductor;
        private String nombre;
        private String apellido;
    }
}
