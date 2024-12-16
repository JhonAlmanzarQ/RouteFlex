package com.software.RouteFlex.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "conductores")
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConductor;

    @Column(unique = true)
    private String nombre;

    private String apellido;

    private String correo;

    private String telefono;

    @Column(name = "contrasena")
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;


}
