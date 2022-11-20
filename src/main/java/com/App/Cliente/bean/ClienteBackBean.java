package com.App.Cliente.bean;

import com.App.Cliente.modelo.dto.PersonaAPI;
import com.App.Cliente.modelo.entity.Persona;
import com.App.Cliente.rest.RestClient;
import com.App.Cliente.servicio.PersonaService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Scope("session")
@Component
public class ClienteBackBean {

    @Getter
    @Setter
    @Scope("session")
    @Component
    public class BaseBackbean implements Serializable {

        private final RestClient restClient;

        private List<Persona> personas;
        private List<PersonaAPI> personaAPIS;

        private Integer id;
        private String primerNombre;
        private String segundoNombre;
        private String primerApellido;
        private String segundoApellido;

        private final PersonaService personaService;

        @Autowired
        public BaseBackbean(RestClient restClient, PersonaService personaService) {
            this.restClient = restClient;
            this.personaService = personaService;
        }

        @PostConstruct
        private void init() {
            limpiar();
        }

        private void obtenerTodo(){
            personas = personaService.obtenerTodo();
        }

        public void guardar() {

            try {
                Persona persona = new Persona();
                persona.setPrimerNombre(primerNombre);
                persona.setSegundoNombre(segundoNombre);
                persona.setPrimerApellido(primerApellido);
                persona.setSegundoApellido(segundoApellido);
                personaService.guardar(persona);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guadado", "Registro guardado con exito"));
                PrimeFaces.current().ajax().update("growl");
                limpiar();
            }catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrio un error al guardar"));
                PrimeFaces.current().ajax().update("growl");
            }

        }

        private void loadFromAPI() {
            personaAPIS = Arrays.asList(restClient.getList("ejercicio/", PersonaAPI.class));
        }


        private void limpiar() {
            obtenerTodo();
            loadFromAPI();
            id = null;
            primerNombre = null;
            segundoNombre = null;
            primerApellido = null;
            segundoApellido = null;
        }

    }

}
