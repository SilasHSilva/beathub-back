package com.project.beathub.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.project.beathub.model.Genero;
import com.project.beathub.model.Musica;
import com.project.beathub.respository.GeneroRepository;
import com.project.beathub.respository.MusicaRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

        @Autowired
        GeneroRepository generoRepository;

        @Autowired
        MusicaRepository musicaRepository;

        @Override
        public void run(String... args) throws Exception {
                generoRepository.saveAll(
                                List.of(
                                                Genero.builder().id(1L).nome("rock").icone("handMetal").build(),
                                                Genero.builder().id(2L).nome("pop").icone("keyboardMusic").build(),
                                                Genero.builder().id(3L).nome("hip-hop").icone("micVocal").build(),
                                                Genero.builder().id(4L).nome("gospel").icone("guitar").build()));

                musicaRepository.saveAll(
                                List.of(
                                                Musica.builder()
                                                                .id(1L)
                                                                .musica("My Love")
                                                                .artista("Justin Timberlake")
                                                                .data(LocalDate.now())
                                                                .genero(generoRepository.findById(2L).get())
                                                                .build(),
                                                Musica.builder()
                                                                .id(2L)
                                                                .musica("Always")
                                                                .artista("Bon Jovi")
                                                                .data(LocalDate.now())
                                                                .genero(generoRepository.findById(1L).get())
                                                                .build(),
                                                Musica.builder()
                                                                .id(5L)
                                                                .musica("Céu Azul")
                                                                .artista("Charlie Brown Junior")
                                                                .data(LocalDate.now())
                                                                .genero(generoRepository.findById(1L).get())
                                                                .build(),
                                                Musica.builder()
                                                                .id(8L)
                                                                .musica("Thank You")
                                                                .artista("Maverick City Music")
                                                                .data(LocalDate.now())
                                                                .genero(generoRepository.findById(4L).get())
                                                                .build()));
        }

}
