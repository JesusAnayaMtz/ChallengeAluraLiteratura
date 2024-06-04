package com.alura.literatura.service;

import com.alura.literatura.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    ILibroRepository libroRepository;
}
