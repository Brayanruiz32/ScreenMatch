package com.example.demo.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.demo.model.DatosSerie;
import com.example.demo.model.DatosTemporadas;
import com.example.demo.model.Serie;
import com.example.demo.service.ConsumoAPI;
import com.example.demo.service.ConvierteDatos;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private String apiKey = "&apiKey=83ba97a4";
    private List<DatosSerie> datosSeries = new ArrayList<>();

    public void muestraElMenu() {
        String menu = """
                1 - Buscar series
                2 - Buscar episodios
                3 - Mostrar series buscadas
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

    private DatosSerie getDatosSerie() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + apiKey);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;

    }

    private void buscarEpisodioPorSerie() {
        DatosSerie datosSerie = getDatosSerie();
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
            String json = consumoApi
                    .obtenerDatos(
                            URL_BASE + datosSerie.titulo().replace(" ", "+") + "&Season=" + i + "&apiKey=83ba97a4");
            var temporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(temporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        datosSeries.add(datos);
        System.out.println(datos);

    }

    private void mostrarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = datosSeries.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());
        series.stream()
            .sorted(Comparator.comparing(Serie::getGenero))
            .forEach(System.out::println);
    }
}
