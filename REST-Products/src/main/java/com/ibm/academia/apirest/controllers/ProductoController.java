package com.ibm.academia.apirest.controllers;

import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> findAll() {
        List<Producto> productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Producto producto, BindingResult bindingResult) {
        Map<String, Object> validations = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage()).collect(Collectors.toList());
            validations.put("Errors", errorList);
            return new ResponseEntity<>(validations, HttpStatus.BAD_REQUEST);
        }
        Producto productoCreated = productoService.save(producto);
        return new ResponseEntity<>(productoCreated, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Producto producto, @PathVariable Long id) {
        Producto updatedProduct = productoService.update(producto, id);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
