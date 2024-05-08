package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SerieDTO;
import com.example.demo.model.Serie;
import com.example.demo.repository.SerieRepository;
import com.example.demo.service.SerieService;

@RestController
public class SerieController {

    @Autowired
    private SerieService servicio;
    
    
    @GetMapping("/series")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }    

    @GetMapping("/series/top5")
    public List<SerieDTO> obtenerTop5(){
        return servicio.obtenerTop5();
    }
    @GetMapping("/series/lanzamientos")
    public List<SerieDTO> obtenerMasRecientes(){
        return servicio.obtenerMasRecientes();
    }


}
