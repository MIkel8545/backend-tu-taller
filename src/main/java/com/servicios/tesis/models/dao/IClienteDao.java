package com.servicios.tesis.models.dao;

import com.servicios.tesis.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IClienteDao extends JpaRepository<Cliente, Long> {
}
