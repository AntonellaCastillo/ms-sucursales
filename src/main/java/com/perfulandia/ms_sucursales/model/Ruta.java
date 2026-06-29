package com.perfulandia.ms_sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

// ENTIDAD RUTA  (CABECERA del recorrido)
// QUÉ es: representa el recorrido de reparto de un transportista.
//         Es la "cabecera" que agrupa varias paradas (Destino).
// CÓMO gestiona el recorrido: agrupa varios Destino ordenados.
//         La relación 1 a muchos (un Ruta tiene muchos Destino) modela
//         que el viaje pasa por varias paradas (A -> B -> C).
// PARA QUÉ: HU-35 (planificar rutas) y HU-56 (el transportista ve sus rutas).
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ruta")
public class Ruta {

    // Clave primaria: la genera la BD sola
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRuta;

    // idTransportista: Id Externo (referencia a un Usuario con rol LOGISTICA
    // que vive en OTRO microservicio). Por eso es solo un Long, no una relación.
    // Dice de QUIÉN es la ruta (HU-56: cada transportista ve solo las suyas).
    @NotNull(message = "El idTransportista es obligatorio")
    private Long idTransportista;

    // Fecha del recorrido
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    // Zona general del reparto (texto)
    @NotBlank(message = "La zona no puede estar vacía")
    private String zona;

    // RELACIÓN 1 a muchos: una Ruta tiene muchos Destino (las paradas).
    // mappedBy = "ruta": el dueño de la relación es el campo "ruta" en Destino.
    // cascade = ALL: si guardo/borro la Ruta, sus Destino se guardan/borran con ella.
    // orphanRemoval = true: si saco un Destino de la lista, se borra de la BD.
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destino> destinos;
}