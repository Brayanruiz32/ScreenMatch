package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Episodio;
import com.example.demo.principal.Principal;
import com.example.demo.repository.EpisodioRepository;
import com.example.demo.repository.SerieRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;
	@Autowired
	private EpisodioRepository repo;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<JpaRepository> repositorios = Arrays.asList(repository, repo);
		Principal miPrincipal = new Principal(repositorios);
		miPrincipal.muestraElMenu();
	}

}
