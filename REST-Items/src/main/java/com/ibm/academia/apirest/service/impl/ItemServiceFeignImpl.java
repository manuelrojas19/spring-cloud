package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.clients.ProductoRestClient;
import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;
import com.ibm.academia.apirest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
public class ItemServiceFeignImpl implements ItemService {

    @Autowired
    private ProductoRestClient client;

    @Override
    public List<Item> findAll() {
        return client.findAll().stream()
                .map(p -> new Item(p, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(client.findById(id), cantidad);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return client.save(producto);
    }

    @Override
    public Producto updateProducto(Producto producto, Long productoId) {
        return client.update(producto, productoId);
    }

    @Override
    public void deleteProducto(Long productoId) {
        client.delete(productoId);
    }
}
