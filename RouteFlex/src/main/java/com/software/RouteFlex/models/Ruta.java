package com.software.RouteFlex.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rutas")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdRuta;

    private String overviewPolyline;

    @ElementCollection
    @CollectionTable(name = "coordenadas_ruta", joinColumns = @JoinColumn(name = "idRuta"))
    @Column(name = "coordenada")
    private List<String> coordenadas;

    @ManyToOne
    @JoinColumn(name = "IdUsuario")
    private Usuario usuario;

}
