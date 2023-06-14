package com.servicios.tesis.models.services;

import com.servicios.tesis.models.entity.Empleado;

import java.util.List;

public interface IEmpleadoService {

    public List<Empleado> findAll();

    public Empleado findById(long id);

    public Empleado save(Empleado empleado);

    public void  delete(long id);
}
