package com.App.Cliente.servicio;

import com.App.Cliente.crud.PersonaCrud;
import com.App.Cliente.modelo.entity.Persona;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaCrud personaCrud;

    public PersonaService(PersonaCrud personaCrud) {
        this.personaCrud = personaCrud;
    }


    public List<Persona> obtenerTodo() {
        return (List<Persona>) personaCrud.findAll();
    }


    public Persona guardar(Persona persona) {
        return personaCrud.save(persona);
    }

}
