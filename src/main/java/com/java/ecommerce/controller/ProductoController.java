package com.java.ecommerce.controller;

import com.java.ecommerce.model.Producto;
import com.java.ecommerce.model.Usuario;
import com.java.ecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

//import java.util.logging.Logger;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    //Se crea una variable Logger para que valla guardando todo lo que se haga en el fron para el back
    private final Logger LOOGER = LoggerFactory.getLogger(ProductoController.class);

    //Se instancia el objeto ProductoService para acceder al crud
    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos", productoService.findAll());
        return "a/productos/show";
    }
    @GetMapping("/create")
    public String create(){
        return "a/productos/create";
    }
    //metodo que va a mappear desde guardar a la BD
    @PostMapping("/save")
    public String save(Producto producto){
        LOOGER.info("Este es el objeto producto {}" + producto);
        //Instanciamos el usuario ficticio por el momento
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        //Metemos el u a producto
        producto.setUsuario(u);
        //guardamos el producto
        productoService.save(producto);
        return "redirect:/productos";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();
        LOOGER.info("Producto buscado: {}" + producto);
        model.addAttribute("productos", producto);
        return "a/productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto){
        productoService.update(producto);
        return "redirect:/productos";
    }


}
