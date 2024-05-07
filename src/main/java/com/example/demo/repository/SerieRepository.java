package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Categoria;
import com.example.demo.model.Episodio;
import com.example.demo.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloIgnoreCase(String Titulo);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    Optional<List<Serie>> findByGenero(Categoria categoriaBusqueda);

    //Optional<List<Serie>> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThan(int totalDeTemporadas, double evaluacion);

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesPorMaxTempYMinEva(int totalTemporadas, double evaluacion);

 



}
