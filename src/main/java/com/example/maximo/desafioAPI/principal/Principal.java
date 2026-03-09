package com.example.maximo.desafioAPI.principal;

import com.example.maximo.desafioAPI.models.*;
import com.example.maximo.desafioAPI.repository.LibroRepository;
import com.example.maximo.desafioAPI.services.ConsumoAPI;
import com.example.maximo.desafioAPI.services.ConvierteDatos;
import com.example.maximo.desafioAPI.repository.AutorRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner entrada = new Scanner(System.in);

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
        1 - Buscar libro por título
        2 - Listar libros registrados
        3 - Listar autores registrados
        4 - Listar autores vivos en un determinado año
        5 - Listar libros por idioma
        0 - Salir
        """);

            try {
                opcion = Integer.parseInt(entrada.nextLine());

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosPorAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saliendo de la aplicacion...");
                        break;
                    default:
                        System.out.println("Opción no valida. Intente denuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduza un número.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String tituloLibro = entrada.nextLine();
        // Buscamos en la API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibros datosLibros = libroBuscado.get();
            System.out.println("Libro encontrado en la API: " + datosLibros.titulo());

            Libro libroExistente = libroRepository.findByTitulo(datosLibros.titulo());
            if (libroExistente != null) {
                System.out.println("Este libro ya se encuentra registrado en la base de datos.");
                return; //
            }

            if (datosLibros.autor() != null && !datosLibros.autor().isEmpty()) {
                DatosAutor datosAutor = datosLibros.autor().get(0);

                Autor autor = autorRepository.findByNombre(datosAutor.nombre());

                if (autor == null) {
                    autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                }

                Libro libro = new Libro(datosLibros, autor);
                libroRepository.save(libro);

                System.out.println("¡Libro y autor guardados exitosamente en la base de datos!");

            } else {
                System.out.println("El libro encontrado no tiene un autor registrado.");
            }

        } else {
            System.out.println("Libro no encontrado en la API Gutendex.");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        System.out.println("\n--- Libros Regristrados ---");
        libros.forEach(System.out::println);
        System.out.println("--------------------------\n");
    }
    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        System.out.println("\n--- Autores Registrados ---");
        autores.forEach(a -> {
            System.out.println("Nombre: " + a.getNombre());
            System.out.println("Fecha de Nacimiento: " + a.getFechaNacimiento());
            System.out.println("Fecha de Fallecimiento: " + a.getFechaFallecimiento());
            System.out.println("-------------------------");
        });
    }
    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingrese el año que desea consultar:");
        try {
            int anio = Integer.parseInt(entrada.nextLine());
            List<Autor> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("\n--- Autores vivos en " + anio + " ---");
                autoresVivos.forEach(a -> System.out.println(a.getNombre()));
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un año válido en formato numérico.");
        }
    }
    private void listarLibrosPorIdioma() {
        System.out.println("""
            Ingrese el idioma para buscar los libros:
            es - Español
            en - Inglés
            fr - Francés
            pt - Portugués
            """);
        String idioma = entrada.nextLine().toLowerCase();

        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en ese idioma.");
        } else {
            System.out.println("\n--- Libros en idioma '" + idioma + "' ---");
            System.out.println("Cantidad de libros: " + librosPorIdioma.size());
            librosPorIdioma.forEach(l -> System.out.println(l.getTitulo()));
        }
    }
}
