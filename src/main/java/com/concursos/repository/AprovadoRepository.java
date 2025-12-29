package com.concursos.repository;

import com.concursos.model.Aprovado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprovadoRepository extends JpaRepository<Aprovado, Long> {

    List<Aprovado> findByNomeContainingIgnoreCase(String nome);

    List<Aprovado> findByEmailContainingIgnoreCase(String email);
}
