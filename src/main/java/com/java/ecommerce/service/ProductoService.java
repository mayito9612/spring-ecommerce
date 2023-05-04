package com.java.ecommerce.service;

import com.java.ecommerce.model.Producto;


import java.util.List;
import java.util.Optional;

public interface ProductoService {
    //Se crea los servicios de producto de tipo interface para el CRUD
    Producto save(Producto producto);
    Optional<Producto> get(Integer id);
    void update(Producto producto);
    void delete(Integer id);
    List<Producto> findAll();
}
