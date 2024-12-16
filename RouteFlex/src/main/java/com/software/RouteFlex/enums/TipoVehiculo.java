package com.software.RouteFlex.enums;

public enum TipoVehiculo {
    PEQUEÑO("Auto pequeño"),
    MEDIANO("Auto mediano"),
    PESADO("Auto pesado");

    private String descripcion;

    TipoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
