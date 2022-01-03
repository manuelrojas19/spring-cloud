package com.ibm.academia.apirest.controller;

import com.ibm.academia.apirest.commons.models.Producto;
import com.ibm.academia.apirest.entities.Item;
import com.ibm.academia.apirest.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/details/products/{productoId}/quantity/{quantity}")
    public ResponseEntity<Item> findDetails(@PathVariable Long productoId, @PathVariable Integer quantity) {
//        return new ResponseEntity<>(itemService.findById(productoId, quantity), HttpStatus.OK);
        return circuitBreakerFactory.create("items").run(
                () -> new ResponseEntity<>(itemService.findById(productoId, quantity), HttpStatus.OK),
                e -> altFindDetails(productoId, quantity, e));
    }

    @CircuitBreaker(name = "items")
    @GetMapping("/details2/products/{productoId}/quantity/{quantity}")
    public ResponseEntity<Item> findDetails2(@PathVariable Long productoId, @PathVariable Integer quantity) {
//        return new ResponseEntity<>(itemService.findById(productoId, quantity), HttpStatus.OK);
        return circuitBreakerFactory.create("items").run(
                () -> new ResponseEntity<>(itemService.findById(productoId, quantity), HttpStatus.OK),
                e -> altFindDetails(productoId, quantity, e)
        );
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
