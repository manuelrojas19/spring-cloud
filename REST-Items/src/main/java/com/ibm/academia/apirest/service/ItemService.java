package com.ibm.academia.apirest.service;

import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
    Producto saveProducto(Producto producto);
    Producto updateProducto(Producto producto, Long idProducto);
    void deleteProducto(Long idProducto);
}
