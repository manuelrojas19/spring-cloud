package com.ibm.academia.apirest.entities;

import com.ibm.academia.apirest.commons.models.Producto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {
    Producto producto;
    Integer cantidad;

    public Double getTotal() {
        return producto.getPrecio() * cantidad.doubleValue();
    }
}
