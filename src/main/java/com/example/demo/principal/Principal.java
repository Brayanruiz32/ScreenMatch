package com.example.demo.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Categoria;
import com.example.demo.model.DatosSerie;
import com.example.demo.model.DatosTemporadas;
import com.example.demo.model.Episodio;
import com.example.demo.model.Serie;
import com.example.demo.repository.EpisodioRepository;
import com.example.demo.repository.SerieRepository;
import com.example.demo.service.ConsumoAPI;
import com.example.demo.service.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private String apiKey = "&apiKey=83ba97a4";
    // private List<DatosSerie> datosSeries = new ArrayList<>();

    private SerieRepository repositorio;
    private EpisodioRepository episodioRepo;
    private List<Serie> series;
    private Optional<Serie> serieBuscada;

    public Principal(List<JpaRepository> repositorios) {

        this.repositorio = (SerieRepository) repositorios.get(0);
        this.episodioRepo = (EpisodioRepository) repositorios.get(1);
    }

    public void muestraElMenu() {
        String menu = """
                1 - Buscar series
                2 - Buscar episodios
                3 - Mostrar series buscadas
                4 - Buscar serie en la bd
                5 - Top 5 series
                6 - Buscar por genero
                7 - Buscar por maximo temp y min de eva
                8 - Buscar episodios por nombre de la BD
                9 - Mostrar top 5 episodios por serie
                0 - Salir
                """;
        System.out.println(menu);
        int opcion = Integer.valueOf(teclado.nextLine());

        while (opcion != 0) {

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSerie();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarPorGenero();
                    break;
                case 7:
                    buscarPorMaxTempYMinEva();
                    break;
                case 8:
                    buscarEpisodioPorNombre();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;

                default:
                    System.out.println("Opcion invalida");
                    break;
            }
            System.out.println(menu);
            opcion = Integer.valueOf(teclado.nextLine());
        }

        // for (DatosTemporadas temporada : temporadas) {
        // List<DatosEpisodio> misEpisodios = temporada.episodios();
        // for (DatosEpisodio episodio : misEpisodios) {
        // System.out.println(episodio.titulo());
        // }
        // }
        // mostrar solo el titulo de los episodios por temporada
        // temporadas.forEach(t -> t.episodios().forEach(e->
        // System.out.println(e.titulo())));

        // mostrar los 5 episodios mejor puntuados
        // List<DatosEpisodio> misEpisodios = temporadas.stream()
        // .flatMap(e ->e.episodios().stream())
        // .collect(Collectors.toList());

        // misEpisodios.stream()
        // .filter(e-> !e.evaluacion().equalsIgnoreCase("N/A"))
        // .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
        // .limit(5)
        // .forEach(System.out::println);
        // convirtiendo los datos a una lista del tipo Episodio
        // List<Episodio> myEpisodes = temporadas.stream()
        // .flatMap(e -> e.episodios().stream()
        // .map(d -> new Episodio(e.numero(), d)))
        // .collect(Collectors.toList());

        // myEpisodes.forEach(System.out::println);

        // // busqueda de episodios a partir de x año
        // System.out.println("Indica el año a partir del cual quieres ver los
        // episodios");
        // var fecha = teclado.nextInt();
        // teclado.nextLine();

        // LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        // myEpisodes.stream()
        // .filter(e -> e.getFechaDeLanzamiento() != null &&
        // e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
        // .forEach(e -> System.out.println(
        // "Temporada " + e.getTemporada() + "Episodio " + e.getTitulo() + "Fecha "
        // + e.getFechaDeLanzamiento().format(dtf)));

        // Busca episodios por pedazo de titulo
        // System.out.println("Escribe tu titulo a buscar o un fragmento");
        // String tituloEpisodio = teclado.nextLine();

        // Optional<Episodio> episodioBuscado = myEpisodes.stream()
        // .filter(t ->
        // t.getTitulo().toUpperCase().contains(tituloEpisodio.toUpperCase()))
        // .findFirst();

        // episodioBuscado.ifPresent(System.out::println);
        // System.out.println(episodioBuscado.orElse("no haye"));

        // Evaluaciones por temporada
        // Map<Integer, Double> evaluacionesPorTemporada = myEpisodes.stream()
        // .filter(e ->e.getEvaluacion() > 0.0 )
        // .collect(Collectors.groupingBy(Episodio::getTemporada,
        // Collectors.averagingDouble(Episodio::getEvaluacion)));

        // System.out.println(evaluacionesPorTemporada);
        // MATEMATICAS ESTADISTICAS DENTRO DE JAVA

        // DoubleSummaryStatistics est = myEpisodes.stream()
        // .filter(t -> t.getEvaluacion() > 0.0)
        // .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        // System.out.println(est);

    }

    private void buscarTop5Episodios() {
        buscarSerie();
  
        if (serieBuscada.isPresent()) {
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = repositorio.obtenerTop5EpisodiosPorSerie(serie);
            topEpisodios.forEach(t -> System.out.println("Titulo "+t.getTitulo()+" evaluacion "+t.getEvaluacion()+" temporada "+t.getTemporada()));
        }else{
            System.out.println("Serie no encontrada");
        }

    }

    private void buscarEpisodioPorNombre() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        String nombre = teclado.nextLine();
        // Optional<List<Episodio>> miEpisodio =
        // Optional.of(repositorio.findTop1ByTituloIgnoreCase(nombre));
        List<Episodio> misEpisodios = repositorio.obtenerEpisodioPorTitulo(nombre);
        misEpisodios.forEach(e -> System.out.println(e.getTitulo() + " " + e.getSerie().getTitulo()));
    }

    private void buscarPorMaxTempYMinEva() {
        System.out.println("Introduce la cantidad maxima de temporadas");
        int cantTemp = Integer.valueOf(teclado.nextLine());
        System.out.println("Introduce la cantidad minima de evaluacion");
        double minEva = Double.valueOf(teclado.nextLine());
        List<Serie> series = repositorio.seriesPorMaxTempYMinEva(cantTemp, minEva);

        series.forEach(t -> System.out.println("Titulo: " + t.getTitulo() + ", temporadas  " + t.getTotalDeTemporadas()
                + ", evaluacion " + t.getEvaluacion()));

        muestraElMenu();
    }

    private void buscarPorGenero() {
        System.out.println("Introduce el genero de serie que deseas buscar");
        var generoBusqueda = teclado.nextLine();
        Categoria categoriaBusqueda = Categoria.fromEspanol(generoBusqueda);
        Optional<List<Serie>> series = repositorio.findByGenero(categoriaBusqueda);
        if (series.isPresent()) {
            series.stream().forEach(
                    s -> s.forEach(t -> System.out.println("Titulo: " + t.getTitulo() + ", Genero: " + t.getGenero())));
        } else {
            System.out.println("No hay series de ese genero");
        }
        muestraElMenu();
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(
                s -> System.out.println("Titulo: " + s.getTitulo() + " con una evaluacion de " + s.getEvaluacion()));
        muestraElMenu();
    }

    private DatosSerie getDatosSerie() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + apiKey);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;

    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("De que serie desea buscar sus episodios");
        String nombreSerie = teclado.nextLine();
        Optional<Serie> serieBusqueda = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase())).findFirst();
        if (serieBusqueda.isPresent()) {
            var serieEncontrada = serieBusqueda.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                String json = consumoApi
                        .obtenerDatos(
                                URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i
                                        + "&apiKey=83ba97a4");
                var temporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(temporada);
            }

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream().map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
        // DatosSerie datosSerie = getDatosSerie();

    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        // datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repositorio.save(serie);// utilizadmos esa instancia de interfaz para guardar
        System.out.println(datos);

    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerie() {
        System.out.println("Que serie deseas buscar?");
        String nombreSerie = teclado.nextLine();
        serieBuscada = repositorio.findByTituloIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()) {
            //System.out.println(serieBuscada.get());

        } else {
            System.out.println("No se encuentra la serie en la BD");
        }
       
    }

}
