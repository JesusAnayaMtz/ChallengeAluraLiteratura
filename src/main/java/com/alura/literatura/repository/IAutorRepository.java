package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
}
