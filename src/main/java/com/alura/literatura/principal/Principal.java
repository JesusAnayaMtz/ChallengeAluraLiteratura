package com.alura.literatura.principal;

import com.alura.literatura.LiteraturaApplication;
import com.alura.literatura.model.*;
import com.alura.literatura.repository.IAutorRepository;
import com.alura.literatura.repository.ILibroRepository;
import com.alura.literatura.service.ConsumoAPI;
import com.alura.literatura.service.ConvierteDatos;
import com.alura.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner sc = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();

    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;

    public Principal(ILibroRepository iLibroRepository, IAutorRepository iAutorRepository){
        this.libroRepository = iLibroRepository;
        this.autorRepository = iAutorRepository;
    }

    public void muestraMenu(){
        var opcion = -1;
        while (opcion != 0){
            System.out.println("Por favor selecciona una opcion");
            System.out.println("1 -- Buscar Libro Por Titulo");
            System.out.println("2 -- Consultar Libros Registrados");
            System.out.println("3 -- Consultar Autores Registrados");
            System.out.println("4 -- Consultar Autores Vivos En Un Determinado Año");
            System.out.println("5 -- Consultar Libros Por Idioma");
            System.out.println("0 -- Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                default:
                    System.out.println("Opcion Invalida Intente una opcion valida o salga del programa");
            }
        }
    }

    private List<DatosLibro> obtenerDatosLibro(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = convierteDatos.obtenerDatos(json, DatosGenerales.class);
        List<DatosGenerales> datosGenerales = new ArrayList<>();
        datosGenerales.add(datos);
        List<DatosLibro> datosLibros = datosGenerales.stream().flatMap(l -> l.libros().stream()).toList();
        return datosLibros;
    }

    private void buscarLibroPorTitulo() {
        List<DatosLibro> datosLibro = obtenerDatosLibro();
        System.out.println("Escriba de favor el titulo del libro a buscar");
        var titulo = sc.nextLine();
        Optional<DatosLibro> busquedaLibro = datosLibro.stream().filter(l -> l.titulo().toUpperCase()
                .contains(titulo.toUpperCase())).findFirst();
        if (busquedaLibro.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println("Los datos del libro son: " + busquedaLibro.get());
            DatosLibro libroEcontrado = busquedaLibro.get();
            Libro libro = new Libro(libroEcontrado);
            Autor autor = libroEcontrado.autores().stream().map()
            autorRepository.save(autor);
            libroRepository.save(libro);

        }else {
            System.out.println("El Libro Que Busca No Se Encontro");
        }
    }
}
