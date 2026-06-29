package com.perfulandia.ms_sucursales.exception;

// EXCEPCIÓN PROPIA
// QUÉ: un error personalizado para cuando algo no existe (sucursal, ruta, destino).
// PARA QUÉ: en vez de lanzar un RuntimeException genérico, lanzo este,
//           que es más claro y el handler lo convierte en un 404.
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}