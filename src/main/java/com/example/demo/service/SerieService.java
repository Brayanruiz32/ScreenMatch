package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.SerieDTO;
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
        return serie.stream().map(s -> new SerieDTO(s.getTitulo(), s.getGenero(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getSinopsis(), s.getPoster(), s.getActores())).collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerMasRecientes() {
        return convierteDatos(repository.lanzamientosMasRecientes());
    }




}
