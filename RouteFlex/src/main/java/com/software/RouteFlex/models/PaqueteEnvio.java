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
@Table(name = "paquetes")
public class PaqueteEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdPaqueteEnvio;

    private String Nombre;

    private String Numero;

    private String Direccion;

    private String Peso;

    private String Fecha;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

}
