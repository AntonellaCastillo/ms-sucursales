package com.perfulandia.ms_sucursales.service;

import com.perfulandia.ms_sucursales.model.Sucursal;
import com.perfulandia.ms_sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.perfulandia.ms_sucursales.exception.RecursoNoEncontradoException;




@Service
public class SucursalService 
{   
    //Comunicacion con el Repositorio
    @Autowired
    private SucursalRepository sucursalRepository;
    
    //Métodos

    //Metodo para guardar
    //Recibe una sucursal y la guarda en la BD
    public Sucursal guardarSucursal(Sucursal sucursal)
    {   
        return sucursalRepository.save(sucursal);
    }

    //Metodo para Listar sucursales
    public List<Sucursal> listarSucursal()
    {
        return sucursalRepository.findAll();
    }
    
    
    //Metodo para buscar sucursales por su id 
    public Optional<Sucursal> obtenerSucursalporId(Long id)
    {
        return sucursalRepository.findById(id);
    }

    public Sucursal actualizarSucursal(Long id, Sucursal sucursal)
    {
        Sucursal existente = sucursalRepository.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("... no existe"));
        existente.setNombre(sucursal.getNombre());
        existente.setDireccion(sucursal.getDireccion());
        existente.setLatitud(sucursal.getLatitud());
        existente.setLongitud(sucursal.getLongitud());
        existente.setHorario(sucursal.getHorario());

        return sucursalRepository.save(existente);
        
    }   

    //Metodo para eliminar una sucursal
    public void eliminarSucursal(Long id)
    {
        sucursalRepository.deleteById(id);
    }
    

    
}
