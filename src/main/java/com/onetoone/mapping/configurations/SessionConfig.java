package com.onetoone.mapping.configurations;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.SessionFactoryServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SessionConfig {
    @Bean
    public SessionFactory sessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(dbSettings())
                .build();
        Metadata metadata = new MetadataSources(serviceRegistry).buildMetadata();
        return metadata.buildSessionFactory();
    }


    private Map dbSettings() {
        return new HashMap() {{
            put("datasource.url", "jdbc:mysql://localhost:3306/users");
            put("datasource.username", "kishan");
            put("datasource.password", "password");
        }};
    }
}
