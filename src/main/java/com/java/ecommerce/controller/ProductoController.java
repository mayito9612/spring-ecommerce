package com.java.ecommerce.controller;

import com.java.ecommerce.model.Producto;
import com.java.ecommerce.model.Usuario;
import com.java.ecommerce.service.ProductoService;
import com.java.ecommerce.service.UploadFileService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/productos")
public class ProductoController {

    //Se crea una variable Logger para que valla guardando todo lo que se haga en el fron para el back
    private final Logger LOOGER = LoggerFactory.getLogger(ProductoController.class);

    //Se instancia el objeto ProductoService para acceder al crud
    @Autowired
    private ProductoService productoService;

    @Autowired
    private UploadFileService upload;

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
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        LOOGER.info("Este es el objeto producto {}" + producto);
        //Instanciamos el usuario ficticio por el momento
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        //Metemos el u a producto
        producto.setUsuario(u);

        //Creamos la logiga para subir la imagen
        if (producto.getId() == null){//Cuando se crea un producto
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }else{

        }
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
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Producto p =  new Producto();
        p =  productoService.get(producto.getId()).get();
        if(file.isEmpty()){//Cuando editamos el producto pero no cambiamos la imagen
            producto.setImagen(p.getImagen());
        }else{//cuando se edita tambien la imagen
            //para eliminar cuando no sea la imagen por defecto
            if (!p.getImagen().equals("default.jpg")){
                upload.deleteImage(p.getImagen());
            }
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Producto p = new Producto();
        p =  productoService.get(id).get();

        //para eliminar ciando no sea la imagen por defecto
        if (!p.getImagen().equals("default.jpg")){
            upload.deleteImage(p.getImagen());
        }
        productoService.delete(id);
        return "redirect:/productos";
    }


}
