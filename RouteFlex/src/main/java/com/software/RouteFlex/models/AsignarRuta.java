package com.software.RouteFlex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignar_rutas")
public class AsignarRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdAsignarRuta;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "IdRuta")
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "IdVehiculo")
    private Vehiculo vehiculo;

    @OneToOne
    @JoinColumn(name = "IdConductor")
    private Conductor conductor;
}
