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
@Table(name = "conductores")
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdConductor;

    private String Nombre;

    private String Apellido;

    private String Correo;

    private String Telefono;

    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

    @OneToOne(mappedBy = "conductor")
    private AsignarRuta asignarRuta;

}
