package com.plocky.deador.repository;

import com.plocky.deador.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByCategory_Id(int categoryId, Pageable pageable);

    Page<Product> findAllByNameContaining(String keyword, Pageable pageable);

    List<Product> findAllByCategory_Id(int id);

    List<Product> findAllByNameContaining(String keyword);

    List<Product> findAllByCategory_IdOrderByPrice(int id);

    List<Product> findAllByCategory_IdOrderByPriceDesc(int id);

}
