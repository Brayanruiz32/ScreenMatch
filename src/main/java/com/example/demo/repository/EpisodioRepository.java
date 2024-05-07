package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Episodio;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {


    //@Query("SELECT e FROM Episodio e WHERE e.titulo LIKE :titulo%")
    //Episodio episodioPorTitulo(String titulo);
    
    //List<Episodio> findTop1ByTituloIgnoreCase(String Titulo);

    Episodio findTop1ByTituloIgnoreCase(String Titulo);

    // List<Episodio> findTop5ByOrderByEvaluacionDesc();
    

}
