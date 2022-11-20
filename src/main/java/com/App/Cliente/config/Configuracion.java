package com.App.Cliente.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Configuracion {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:sqlserver://DESKTOP-NEVCUIL;database=Autolote;trustServerCertificate=true");
        builder.username("login");
        builder.password("1234");
        return builder.build();
    }

}
