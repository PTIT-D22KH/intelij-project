package vn.app.duongvct.service;

import org.springframework.stereotype.Service;
import vn.app.duongvct.domain.Product;
import vn.app.duongvct.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
