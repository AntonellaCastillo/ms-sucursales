package com.perfulandia.ms_sucursales.repository;

import com.perfulandia.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CAPA REPOSITORY
// Es la capa que se comunica con la base de datos.

// @Repository: anotación de Spring que marca esta interface como la capa
// que se comunica con la BD

// extends JpaRepository<Sucursal, Long>: le indica que trabajará con la

// entidad Sucursal (cuyo id es de tipo Long). Así hereda de JpaRepository

// todo el CRUD ya hecho (save, findById, findAll, delete...) sin escribir SQL

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>
{
    
}