package com.servicios.tesis.controllers;

import com.servicios.tesis.models.entity.Cliente;

import com.servicios.tesis.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")


public class ClienteRestController {

    @Autowired
    private IClienteService clienteService;


//Obtener todos los clientes
    @GetMapping("/clientes")
    public List<Cliente> index(){
        return clienteService.findAll();
    }

    @GetMapping("/clientes/page/{page}")
    public Page<Cliente> index(@PathVariable Integer page){
        Pageable pageable =PageRequest.of(page, 5);
        return clienteService.findAll(pageable);
    }

    //Obtener un cliente
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try{
            cliente = clienteService.findById(id);
        }
        //Error al consultar con la base de datos
        catch (DataAccessException e){
            response.put("mensaje","Error al realizar la consulta");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        //Manejo de Errores
        if(cliente == null){
            response.put("mensaje","El cliente ID: ".concat(id.toString().concat(", No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        //Encuentra y retorna el cliente con status 200
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    //Crear un cliente
    @PostMapping("/clientes")
    public ResponseEntity<?> create(@RequestBody Cliente cliente){

        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clienteNew = clienteService.save(cliente);
        }
        catch (DataAccessException e){
            response.put("mensaje","Error al crear un nuevo Cliente");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","El cliente ha sido creado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>( response, HttpStatus.CREATED) ;
    }

    //Actualizar Cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;


        if(clienteActual == null){
            response.put("mensale","Error: El cliente ID: ".concat(id.toString().concat(", No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try{
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setTelefono(cliente.getTelefono());
            clienteActual.setDireccion(cliente.getDireccion());
            clienteActual.setRfc(cliente.getRfc());

            clienteUpdated =    clienteService.save(clienteActual);

        }
        catch (DataAccessException e)
        {
            response.put("mensaje","Error al editar al  Cliente");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }


        response.put("mensaje","El cliente ha sido editado con éxito");
        response.put("cliente", clienteUpdated);


        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = clienteService.findById(id);
        try{

            String nombreFotoAnterior = cliente.getFoto();

            if(nombreFotoAnterior!=null && nombreFotoAnterior.length() >0 ){
                Path rutaFotoAnterior= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior= rutaFotoAnterior.toFile();

                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }
            clienteService.delete(id);
        }
        catch (DataAccessException e){
            response.put("mensaje", "Error al eliminar al cliente de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);

    }

    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("id") Long id){
        Map <String,Object> response = new HashMap<>();
        Cliente cliente = clienteService.findById(id);

        if(!archivo.isEmpty()){
            String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();

            try{
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e)
            {
                response.put("mensaje", "Error al subir la imagen del cliente" + nombreArchivo);
                response.put("error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String nombreFotoAnterior = cliente.getFoto();

            if(nombreFotoAnterior!=null && nombreFotoAnterior.length() >0 ){
                Path rutaFotoAnterior= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
                File archivoFotoAnterior= rutaFotoAnterior.toFile();

                if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                    archivoFotoAnterior.delete();
                }
            }

            cliente.setFoto(nombreArchivo);
            clienteService.save(cliente);

            response.put("cliente", cliente);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);


        }


        return  new ResponseEntity<Map<String, Object>>( response,HttpStatus.CREATED);
    }
    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public  ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso = null;

        try{
            recurso = new UrlResource(rutaArchivo.toUri());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        if(!recurso.exists() && !recurso.isReadable()){
            throw new RuntimeException("Error no se pudo cargara la imagen: " + nombreFoto);
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + recurso.getFilename() + "\"" );



        return  new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
    }

}
