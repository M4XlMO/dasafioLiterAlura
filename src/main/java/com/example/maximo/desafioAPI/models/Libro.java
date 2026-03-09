package com.example.maximo.desafioAPI.models;

import jakarta.persistence.* ;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idioma;

    private Double numeroDeDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibros datosLibros, Autor autor) {
        this.titulo = datosLibros.titulo();
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();

        if (datosLibros.idiomas() != null && !datosLibros.idiomas().isEmpty()) {
            this.idioma = datosLibros.idiomas().get(0);
        }

        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro [titulo=" + titulo + ", idioma=" + idioma + ", autor=" + autor.getNombre() + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}