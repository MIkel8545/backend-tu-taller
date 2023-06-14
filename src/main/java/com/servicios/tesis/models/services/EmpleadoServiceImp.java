package com.servicios.tesis.models.services;

import com.servicios.tesis.models.dao.IEmpleadoDao;
import com.servicios.tesis.models.entity.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoServiceImp implements IEmpleadoService{


    @Autowired
    private IEmpleadoDao empleadoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> findAll() {
        return (List<Empleado>) empleadoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Empleado findById(long id) {
        return empleadoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Empleado save(Empleado empleado) {
        return empleadoDao.save(empleado);
    }

    @Override
    @Transactional
    public void delete(long id) {
        empleadoDao.deleteById(id);
    }

}
