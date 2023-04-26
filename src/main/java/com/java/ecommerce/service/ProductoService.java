package com.java.ecommerce.service;

import com.java.ecommerce.model.Producto;


import java.util.Optional;

public interface ProductoService {
    //Se crea los servicios de producto de tipo interface para el CRUD
    public Producto save(Producto producto);
    public Optional<Producto> get(Integer id);
    public void update(Producto producto);
    public void delete(Integer id);
}
