package com.ibm.academia.apirest.clients;

import com.ibm.academia.apirest.commons.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "rest-products")
public interface ProductoRestClient {
    @GetMapping("/api/v1/products")
    List<Producto> findAll();

    @GetMapping("/api/v1/products/{id}")
    Producto findById(@PathVariable Long id);

    @PostMapping("/api/v1/products")
    Producto save(@RequestBody Producto producto);

    @PutMapping("/api/v1/products/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable Long id);

    @DeleteMapping("/api/v1/products/{id}")
    Producto delete(@PathVariable Long id);
}
