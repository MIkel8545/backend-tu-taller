package com.servicios.tesis.controllers;

import com.servicios.tesis.models.entity.Servicio;
import com.servicios.tesis.models.services.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class ServicioRestController {

    @Autowired
    private IServicioService empleadoService;

    @GetMapping("/servicios")
    public List<Servicio> index(){
        return empleadoService.findAll();
    }

    @GetMapping("/servicios/{id}")
    public Servicio show(@PathVariable Long id){
        return empleadoService.findById(id);
    }

    @PostMapping("/servicios")
    @ResponseStatus(HttpStatus.CREATED)
    public Servicio create(@RequestBody Servicio empleado){
        return empleadoService.save(empleado);
    }

    @PutMapping("/servicios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Servicio update(@RequestBody Servicio empleado, @PathVariable Long id){
        Servicio empleadoActual = empleadoService.findById(id);

        empleadoActual.setCliente(empleado.getCliente());
        empleadoActual.setMecanico(empleado.getMecanico());
        empleadoActual.setModelo(empleado.getModelo());
        empleadoActual.setMarca(empleado.getMarca());
        empleadoActual.setTipo(empleado.getTipo());
        empleadoActual.setSerie(empleado.getSerie());
        empleadoActual.setColor(empleado.getColor());
        empleadoActual.setKm(empleado.getKm());


        return empleadoService.save(empleadoActual);
    }

    @DeleteMapping("/servicios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        empleadoService.delete(id);
    }
}
