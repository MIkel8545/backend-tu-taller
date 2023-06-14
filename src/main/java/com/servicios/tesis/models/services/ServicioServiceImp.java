package com.servicios.tesis.models.services;

import com.servicios.tesis.models.dao.IServicioDao;
import com.servicios.tesis.models.entity.Empleado;
import com.servicios.tesis.models.entity.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioServiceImp implements IServicioService{


    @Autowired
    private IServicioDao servicioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> findAll() {
        return (List<Servicio>)servicioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio findById(long id) {
        return servicioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Servicio save(Servicio servicio) {
        return servicioDao.save(servicio);
    }

    @Override
    @Transactional
    public void delete(long id) {
        servicioDao.deleteById(id);
    }

}
