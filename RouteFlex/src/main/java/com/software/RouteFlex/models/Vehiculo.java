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
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdVehiculo;

    private String TipoVehiculo;

    private Long Peso;

    private String Marca;

    private String Placa;

    private boolean Estado;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

}
