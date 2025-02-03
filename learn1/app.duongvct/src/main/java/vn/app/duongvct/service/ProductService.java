package vn.app.duongvct.service;

import org.springframework.stereotype.Service;
import vn.app.duongvct.domain.Product;
import vn.app.duongvct.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

}
