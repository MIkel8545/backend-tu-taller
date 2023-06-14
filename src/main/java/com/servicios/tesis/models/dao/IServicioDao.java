package com.servicios.tesis.models.dao;



import com.servicios.tesis.models.entity.Servicio;
import org.springframework.data.repository.CrudRepository;

public interface IServicioDao  extends CrudRepository<Servicio, Long> {
}
