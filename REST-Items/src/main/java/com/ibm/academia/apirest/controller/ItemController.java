package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;
import com.ibm.academia.apirest.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    @Qualifier("itemServiceFeignImpl")
    private ItemService itemService;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * Endpoint para listar los items
     *
     * @return Retorna una lista de items
     * @author MARR - 10-12-2021
     */
    @GetMapping
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Endpoint para recuperar los detalles del item
     *
     * @return Retorna un objeto con los detalles del item
     * @author MARR - 10-12-2021
     */
//    @HystrixCommand(fallbackMethod = "altFindDetails")
    @GetMapping("/details/productos/{productoId}/quantity/{quantity}")
    public ResponseEntity<Item> findDetails(@PathVariable Long productoId, @PathVariable Integer quantity) {
        return circuitBreakerFactory.create("items").run(() -> new ResponseEntity<>(itemService
                .findById(productoId, quantity), HttpStatus.OK), e -> altFindDetails(productoId, quantity, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "altFindDetails2")
    @GetMapping("/details2/productos/{productoId}/quantity/{quantity}")
    public CompletableFuture<Item> findDetails2(@PathVariable Long productoId, @PathVariable Integer quantity) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(productoId, quantity));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "altFindDetails2")
    @TimeLimiter(name = "items")
    @GetMapping("/details3/productos/{productoId}/quantity/{quantity}")
    public CompletableFuture<Item> findDetails3(@PathVariable Long productoId, @PathVariable Integer quantity) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(productoId, quantity));
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> saveProducto(@RequestBody Producto producto) {
        Producto response = itemService.saveProducto(producto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/productos/{productoId}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto, @PathVariable Long productoId) {
        Producto response = itemService.updateProducto(producto, productoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/productos/{productoId}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long productoId) {
        itemService.deleteProducto(productoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Item> altFindDetails(Long productoId, Integer cantidad, Throwable e) {
        log.info("Throwable --> {}", e.getMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(productoId);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    public CompletableFuture<Item> altFindDetails2(Long productoId, Integer cantidad, Throwable e) {
        log.info("Throwable ---> {}", e.getMessage());

        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(productoId);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return CompletableFuture.supplyAsync(() -> item);
    }

}
