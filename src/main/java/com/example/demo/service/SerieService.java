package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.EpisodioDTO;
import com.example.demo.dto.SerieDTO;
import com.example.demo.model.Categoria;
import com.example.demo.model.Serie;
import com.example.demo.repository.SerieRepository;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;
    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }
    
    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> convierteDatos(List<Serie> serie){
        return serie.stream().map(s -> new SerieDTO(s.getId(),s.getTitulo(), s.getGenero(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getSinopsis(), s.getPoster(), s.getActores())).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerMasRecientes() {
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie miSerie = serie.get();
            return new SerieDTO(miSerie.getId(),miSerie.getTitulo(), miSerie.getGenero(), miSerie.getTotalDeTemporadas(), miSerie.getEvaluacion(), miSerie.getSinopsis(), miSerie.getPoster(), miSerie.getActores());
        }
      return null;
    }

    public List<EpisodioDTO> obtenerTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            return serie.get().getEpisodios().stream().map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio())).collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTemporadasEspecifica(Long id, int idTemporada) {
        List<EpisodioDTO> episodioTemporada = obtenerTemporadas(id);
        return episodioTemporada.stream().filter( e -> e.temporada() == idTemporada).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerPorCategoria(String categoria) {
        Categoria categoriaBuscada = null;
        try {
            categoriaBuscada = Categoria.fromEspanol(categoria);    
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        Optional<List<Serie>> series = repository.findByGenero(categoriaBuscada);
        if (series.isPresent()) {
            return convierteDatos(series.get());
        }

        //List<Serie> series = repository.consultaPorCategoria(categoriaBuscada);
        return null;
    }
}
