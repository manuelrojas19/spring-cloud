package com.ibm.academia.apirest.service;

import com.ibm.academia.apirest.commons.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    Producto update(Producto producto, Long id);
    void delete(Long id);
}
