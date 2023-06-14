package com.servicios.tesis.models.dao;
import com.servicios.tesis.models.entity.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface IEmpleadoDao extends CrudRepository<Empleado, Long> {
}
