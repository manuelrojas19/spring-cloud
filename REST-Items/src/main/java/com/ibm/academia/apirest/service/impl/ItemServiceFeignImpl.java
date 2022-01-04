package com.ibm.academia.apirest.service.impl;

import brave.Tracer;
import com.ibm.academia.apirest.clients.ProductoRestClient;
import com.ibm.academia.apirest.commons.exceptions.NotFoundException;
import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;
import com.ibm.academia.apirest.service.ItemService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service
@Slf4j
public class ItemServiceFeignImpl implements ItemService {

    @Autowired
    private ProductoRestClient client;

    @Autowired
    private Tracer tracer;

    @Override
    public List<Item> findAll() {
        return client.findAll().stream()
                .map(p -> new Item(p, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Item item;
        try {
            item = new Item(client.findById(id), cantidad);
        } catch (FeignException e) {
            tracer.currentSpan().tag("error.message", "El producto con id " + id + " no existe en la BD.");
            log.error(e.getMessage());
            throw new NotFoundException(e.getMessage());
        }
        return item;
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
