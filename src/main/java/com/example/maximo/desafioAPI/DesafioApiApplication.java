package com.example.maximo.desafioAPI;

import com.example.maximo.desafioAPI.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.maximo.desafioAPI.repository.LibroRepository;
import com.example.maximo.desafioAPI.principal.Principal;

@SpringBootApplication
public class DesafioApiApplication implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(DesafioApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroRepository, autorRepository);
        principal.muestraMenu();
    }
}