package com.servicios.tesis.models.services;

import com.servicios.tesis.models.entity.Empleado;
import com.servicios.tesis.models.entity.Servicio;

import java.util.List;

public interface IServicioService {

    public List<Servicio> findAll();

    public Servicio findById(long id);

    public Servicio save(Servicio servicio);

    public void  delete(long id);
}
