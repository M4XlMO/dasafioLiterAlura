package com.example.maximo.desafioAPI.repository;

import com.example.maximo.desafioAPI.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Derived query para buscar libros por un idioma específico
    List<Libro> findByIdioma(String idioma);
    // Derived query para buscar libros por un titulo específico
    Libro findByTitulo(String titulo);
}