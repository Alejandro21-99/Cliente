package com.App.Cliente.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Component
public class RestClient {

    private String strAuth = "1234:1234";
    private HttpHeaders httpHeaders = new HttpHeaders();
    private HttpEntity httpEntity;

    private final String server = "http://localhost:80/";
    private final RestTemplate rest;
    private final HttpHeaders headers;

    @Setter
    private HttpStatus status;


    public String getAllVehiculos(){
        String url = "http://localhost:8091/custom/vehiculo/todo";
        String base64Creds = Base64.getEncoder().encodeToString(strAuth.getBytes());

        httpHeaders.add("Authorization", "Basic " + base64Creds);
        httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, httpEntity , String.class);

        // get JSON response
        String json = response.getBody();

        return json;

    }

    public String getAllPersonas() {
        String url = "http://localhost:8091/custom/persona/todo";

        // create auth credentials
        String authStr = "1234:1234";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        // create request
        HttpEntity request = new HttpEntity(headers);

        // make a request
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        // get JSON response
        String json = response.getBody();

        return json;
    }
    public RestClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode("user:1234".getBytes())));
    }

    public <T> T[] getList(String uri, Class<T> _class) {
        HttpEntity<T[]> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        TypeToken<?> listType = TypeToken.getArray(_class);

        return (T[]) new Gson().fromJson(responseEntity.getBody(),listType);
//        return (T[]) rest.getForObject(server + uri, listType.getRawType());
    }

    public <T> T get(String uri, Class<T> _class, Map<String, ?> query) {
        HttpEntity<T[]> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class, query);
        this.setStatus(responseEntity.getStatusCode());
        return new Gson().fromJson(responseEntity.getBody(),_class);
    }

    public <T> T getOne(String uri, Class<T> _class) {
        HttpEntity<T[]> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return new Gson().fromJson(responseEntity.getBody(),_class);
    }

    public <T> T get(String uri, Class<T> _class) {
        HttpEntity<T[]> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return new Gson().fromJson(responseEntity.getBody(),_class);
    }

    public <T> T post(String uri, T obj) {
        String json = new Gson().toJson(obj);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return (T) new Gson().fromJson(responseEntity.getBody(),obj.getClass());
    }

}
