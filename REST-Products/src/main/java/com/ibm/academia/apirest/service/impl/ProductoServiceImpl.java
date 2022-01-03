package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.commons.exceptions.NotFoundException;
import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.repositories.ProductoRepository;
import com.ibm.academia.apirest.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    private static final String NOT_FOUND_ERROR_MSG = "No se encontraron productos";

    @Autowired
    ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        List<Producto> productos = (List<Producto>) productoRepository.findAll();
        if (productos.isEmpty())
            throw new NotFoundException(NOT_FOUND_ERROR_MSG);
        return productos;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto update(Producto producto, Long id) {
        Producto productoToUpdate = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
        productoToUpdate.setNombre(producto.getNombre());
        productoToUpdate.setPrecio(producto.getPrecio());
        return productoRepository.save(productoToUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Producto productoToDelete = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR_MSG));
        productoRepository.delete(productoToDelete);
    }
}
