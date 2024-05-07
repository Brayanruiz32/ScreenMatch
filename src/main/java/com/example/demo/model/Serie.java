package com.example.demo.model;

import java.util.List;
import java.util.OptionalDouble;

import jakarta.persistence.*;

@Entity
 @Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private Integer totalDeTemporadas;

    private Double evaluacion;

    private String sinopsis;

    private String poster;

    private String actores;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    public Serie() {
    }

    public Serie(DatosSerie datos) {
        this.titulo = datos.titulo();
        this.totalDeTemporadas = datos.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datos.evaluacion())).orElse(0);
        this.genero = Categoria.fromString(datos.genero().split(",")[0].trim());
        this.sinopsis = datos.sinopsis();
        this.poster = datos.poster();
        this.actores = datos.actores();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActores() {
        return actores;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        //al momento de persistir la serie en la BD, guardamos la lista de episodios con su serie como tal
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }



    @Override
    public String toString() {
        return "Episodio: Id=" + Id + ", titulo=" + titulo + ", genero=" + genero + ", totalDeTemporadas="
                + totalDeTemporadas + ", evaluacion=" + evaluacion + ", sinopsis=" + sinopsis + ", poster=" + poster
                + ", actores=" + actores + ", episodios=" + episodios + "]";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

}
