package com.example.demo.dto;

import com.example.demo.model.Categoria;

public record SerieDTO(
        String titulo,
        Categoria genero,
        Integer totalDeTemporadas,
        Double evaluacion,
        String sinopsis,
        String poster,
        String actores) {

}
