package com.servicios.tesis.controllers;

import com.servicios.tesis.models.entity.Empleado;
import com.servicios.tesis.models.services.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping("/empleados")
    public List<Empleado> index(){
        return empleadoService.findAll();
    }

    @GetMapping("/empleados/{id}")
    public Empleado show(@PathVariable Long id){
        return empleadoService.findById(id);
    }

    @PostMapping("/empleados")
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado create(@RequestBody Empleado empleado){
        return empleadoService.save(empleado);
    }

    @PutMapping("/empleados/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado update(@RequestBody Empleado empleado, @PathVariable Long id){
        Empleado empleadoActual = empleadoService.findById(id);

        empleadoActual.setApellido(empleado.getApellido());
        empleadoActual.setNombre(empleado.getNombre());
        empleadoActual.setEmail(empleado.getEmail());
        empleadoActual.setDireccion(empleado.getDireccion());
        empleadoActual.setTelefono(empleado.getTelefono());
        empleadoActual.setPuesto(empleado.getPuesto());


        return empleadoService.save(empleadoActual);
    }

    @DeleteMapping("/empleados/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        empleadoService.delete(id);
    }
}
