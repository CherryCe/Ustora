package com.edu.hutech.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.edu.hutech.major.model.Product;

@Service
public interface ProductService {
	List<Product> getAllProduct();

	void updateProduct(Product product);

	void removeProductById(long id);

	Optional<Product> getProductById(long id);

	List<Product> getAllProductByCategoryId(int id);

	List<Product> getAllProductBySortASC();

	List<Product> getAllProductBySortDESC();

	Page<Product> getProductByPagination(int offset);
}
