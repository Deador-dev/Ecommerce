package com.plocky.deador.service;

import com.plocky.deador.entity.Product;
import com.plocky.deador.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    public ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProductById(long id) {
        productRepository.deleteAllById(Collections.singleton(id));
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }

    public List<Product> getAllProductsByNameContains(String keyword) {
        return productRepository.findAllByNameContaining(keyword);
    }

    public List<Product> getAllProductsByCategoryIdOrderByPrice(int id) {
        return productRepository.findAllByCategory_IdOrderByPrice(id);
    }

    public List<Product> getAllProductsByCategoryIdOrderByPriceDesc(int id) {
        return productRepository.findAllByCategory_IdOrderByPriceDesc(id);
    }

    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAll(pageable);
    }

    public Page<Product> findPaginatedInCategory(int pageNo, int pageSize, String sortField, String sortDirection, int categoryId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAllByCategory_Id(categoryId, pageable);
    }

    public Page<Product> findPaginatedInSearch(int pageNo, int pageSize, String sortField, String sortDirection, String keyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productRepository.findAllByNameContaining(keyword, pageable);
    }

}
