package com.ibm.academia.apirest.repositories;

import com.ibm.academia.apirest.commons.models.Producto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long> {
}
