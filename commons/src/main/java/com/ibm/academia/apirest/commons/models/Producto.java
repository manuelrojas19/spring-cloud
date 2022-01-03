package com.ibm.academia.apirest.commons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Size(min = 5, max = 50)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Positive(message = "El valor debe ser mayor a cero")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Size(min = 5, max = 50)
    @Column(name = "usuario_creacion", nullable = false)
    private String usuarioCreacion;

    @NotNull(message = "No puede ser nulo")
    @NotEmpty(message = "No puede ser vacio")
    @Size(min = 5, max = 50)
    @Column(name = "fechaCreacion", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = new Date();
    }

    private static final long serialVersionUID = 7249415008808960857L;

}
