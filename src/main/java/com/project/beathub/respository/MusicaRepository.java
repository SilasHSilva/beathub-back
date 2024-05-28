package com.project.beathub.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.beathub.model.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    Page<Musica> findByGeneroNome(String Genero, Pageable pageable);

    @Query("SELECT m FROM Musica m WHERE MONTH(m.data) = ?1")
    Page<Musica> findByMes(Integer mes, Pageable pageable);

}
