package com.edu.hutech.major.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.edu.hutech.major.model.Product;
import com.edu.hutech.major.repository.ProductRepository;
import com.edu.hutech.major.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}// findAll

	public void updateProduct(Product product) {
		productRepository.save(product);
	}// add or update (tuy vao pri-key)

	public void removeProductById(long id) {
		productRepository.deleteById(id);
	}// delete dua vao pri-key

	public Optional<Product> getProductById(long id) {
		return productRepository.findById(id);
	}// search theo id

	public List<Product> getAllProductByCategoryId(int id) {
		return productRepository.findAllByCategory_Id(id);
	}
	// findList theo ProductDTO.categoryId

	public List<Product> getAllProductBySortASC() {
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
	}

	public List<Product> getAllProductBySortDESC() {
		return productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
	}

	public Page<Product> getProductByPagination(int offset) {
		return productRepository.findAll(PageRequest.of(offset, 8));
	}
}
