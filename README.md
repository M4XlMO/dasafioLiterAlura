# 📚 LiterAlura - Catálogo de Libros

Un catálogo de libros interactivo por consola (CLI) construido con Java y Spring Boot. Esta aplicación consume la [API de Gutendex](https://gutendex.com/) (basada en el Proyecto Gutenberg) para buscar libros y autores, y persiste los datos en una base de datos PostgreSQL.

## ✨ Funcionalidades
El proyecto cuenta con un menú interactivo que permite al usuario:
1. **Buscar libro por título:** Consulta la API de Gutendex, trae los datos del libro y su autor, y los guarda en la base de datos local (evitando duplicados).
2. **Listar libros registrados:** Muestra todos los libros que han sido guardados en la base de datos local.
3. **Listar autores registrados:** Muestra todos los autores guardados junto con sus años de nacimiento y fallecimiento.
4. **Listar autores vivos en un determinado año:** Filtra los autores de la base de datos que estaban vivos en el año ingresado por el usuario usando *Derived Queries*.
5. **Listar libros por idioma:** Permite buscar cuántos y cuáles libros están registrados en la base de datos según su idioma (ej. `es`, `en`, `fr`, `pt`).

## 🛠️ Tecnologías Utilizadas
* **Spring Boot** (CommandLineRunner)
* **Spring Data JPA** (Hibernate)
* **PostgreSQL**
* **Jackson** (ObjectMapper para la deserialización de JSON)
* **HttpClient** (Para solicitudes HTTP a la API externa)

## 🚀 Instalación y Ejecución

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/M4XlMO/dasafioLiterAlura.git
