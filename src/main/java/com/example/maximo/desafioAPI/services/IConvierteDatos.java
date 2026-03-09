package com.example.maximo.desafioAPI.services;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
