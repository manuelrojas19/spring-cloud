package com.ibm.academia.apirest.service.impl;

import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;
import com.ibm.academia.apirest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceRestTemplateImpl implements ItemService {
    private static final String PRODUCTS_URL = "http://rest-products/api/v1/products";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return null;
    }

    @Override
    public Producto saveProducto(Producto producto) {
        HttpEntity<Producto> request = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = restTemplate
                .exchange(PRODUCTS_URL, HttpMethod.POST, request, Producto.class);
        return response.getBody();
    }

    @Override
    public Producto updateProducto(Producto producto, Long idProducto) {
        Map<String, String> variables = new HashMap<>();
        variables.put("idProducto", idProducto.toString());

        HttpEntity<Producto> request = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = restTemplate
                .exchange(PRODUCTS_URL + "/{idProducto}", HttpMethod.PUT, request, Producto.class, variables);
        return response.getBody();
    }

    @Override
    public void deleteProducto(Long idProducto) {
        Map<String, String> variables = new HashMap<>();
        variables.put("id", idProducto.toString());

        restTemplate.delete(PRODUCTS_URL + "/{id}", variables);
    }
}
