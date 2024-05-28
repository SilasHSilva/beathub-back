package com.project.beathub.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.beathub.model.Genero;
import com.project.beathub.respository.GeneroRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("genero")
@CacheConfig(cacheNames = "generos")
@Slf4j
public class GeneroController {

    @Autowired
    GeneroRepository repository;

    @GetMapping
    @Cacheable
    public List<Genero> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @CacheEvict(allEntries = true)
    @Operation
    public Genero create(@RequestBody @Valid Genero genero) {
        log.info("cadastrando genero " + genero);
        return repository.save(genero);
    }

    @GetMapping("{id}")
    public ResponseEntity<Genero> show(@PathVariable Long id) {
        log.info("buscar genero por id {} ", id);

        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando genero com id {} ", id);

        verifyIfExists(id);
        repository.deleteById(id);
    }

    @PutMapping("{id}")
    @CacheEvict(allEntries = true)
    public Genero atualizar(@PathVariable Long id, @RequestBody Genero genero) {
        log.info("atualizando genero com id {} para {}", id, genero);

        verifyIfExists(id);
        genero.setId(id);
        return repository.save(genero);

    }

    private void verifyIfExists(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Não existe genero com o id informado"));
    }

}
