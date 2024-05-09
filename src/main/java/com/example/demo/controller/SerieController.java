package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EpisodioDTO;
import com.example.demo.dto.SerieDTO;
import com.example.demo.model.Serie;
import com.example.demo.repository.SerieRepository;
import com.example.demo.service.SerieService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servicio;
    
    
    @GetMapping("")
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }    

    @GetMapping("/top5")
    public List<SerieDTO> obtenerTop5(){
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerMasRecientes(){
        return servicio.obtenerMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    };

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTemporadas(@PathVariable Long id) {
        return servicio.obtenerTemporadas(id);
    }
    
    @GetMapping("/{id}/temporadas/{idTemporada}")
    public List<EpisodioDTO> obtenerTemporadas(@PathVariable Long id, @PathVariable int idTemporada) {
        return servicio.obtenerTemporadasEspecifica(id, idTemporada);
    }


}
