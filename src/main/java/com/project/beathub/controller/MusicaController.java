package com.project.beathub.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.beathub.model.Musica;
import com.project.beathub.respository.MusicaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("musica")
public class MusicaController {

    record TotalPorGenero(String genero, String nome) {
    }

    @Autowired
    MusicaRepository repository;

    @GetMapping
    public Page<Musica> index(
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) Integer mes,
            @PageableDefault(size = 4, sort = "data", direction = Direction.DESC) Pageable pageable) {

        if (mes != null) {
            return repository.findByMes(mes, pageable);
        }

        if (genero != null) {
            return repository.findByGeneroNome(genero, pageable);
        }

        return repository.findAll(pageable);
    }

    @GetMapping("totais-por-genero")
    public List<TotalPorGenero> getTotaisPorGenero() {

        var musica = repository.findAll();

        Map<String, List<Musica>> collect = musica.stream()
                .collect(
                        Collectors.groupingBy(
                                m -> m.getGenero().getNome()));

        return collect
                .entrySet()
                .stream()
                .map(e -> new TotalPorGenero(e.getKey(), e.getKey()))
                .toList();

    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Musica create(@RequestBody @Valid Musica musica) {
        return repository.save(musica);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
