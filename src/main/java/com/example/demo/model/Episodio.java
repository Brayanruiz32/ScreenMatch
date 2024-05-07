package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;



@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer temporada;

    private String titulo;

    private Integer numeroEpisodio;

    private Double evaluacion;

    private LocalDate fechaDeLanzamiento;

    @ManyToOne
    private Serie serie;
    
    public Episodio(){
        
    }

    public Episodio(Integer numero, DatosEpisodio p) {
        this.temporada = numero ;
        this.titulo = p.titulo();
        this.numeroEpisodio = p.numeroEpisodio();
        try {
            this.evaluacion = Double.valueOf(p.evaluacion());    
        } catch (Exception e) {
           this.evaluacion = 0.0;
        }
        try {
            this.fechaDeLanzamiento = LocalDate.parse(p.fechaDeLanzamiento());    
        } catch (Exception e) {
            this.fechaDeLanzamiento = null;
        }
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public String toString() {
        return "Episodio [temporada=" + temporada + ", titulo=" + titulo + ", numeroEpisodio=" + numeroEpisodio
                + ", evaluacion=" + evaluacion + ", fechaDeLanzamiento=" + fechaDeLanzamiento + "]";
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}
