package com.perfulandia.ms_sucursales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

// ENTIDAD SUCURSAL

//Representa la tabla "sucursal" en la BD. Cada objeto es una fila.
//@Data genera getters/setters automáticamente (Lombok)
@Data
@Entity
@Table (name = "sucursal")
public class Sucursal 
{  
    //Clave primaria: la genera la BD sola (autoincremento) 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSucursal;

    //Datos de texto: 
    // @NotBlank valida que no lleguen vacíos
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    
    //Coordenadas (decimales):
    //BigDecimal: para tener decimales exactos, sin perder precisión
    //@NotNull para que la coordenada no llegue vacía 
    //@DecimalMin para validar que esté dentro de un rango geografico válido
    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin(value = "-90.0", message = "La latitud mínima es -90")
    private BigDecimal latitud;

    @NotNull(message = "La longitud es obligatoria" )
    @DecimalMin(value= "-180.0", message = "La longitud mínima es -180")
    private BigDecimal longitud;

    //Horario (texto): @NotBlank valida que no llegue vacío
    @NotBlank(message = "El horario no puede estar vacío")
    private String horario;
}  


