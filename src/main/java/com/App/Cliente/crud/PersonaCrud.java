package com.App.Cliente.crud;

import com.App.Cliente.modelo.entity.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaCrud extends CrudRepository<Persona, Integer> {
}
