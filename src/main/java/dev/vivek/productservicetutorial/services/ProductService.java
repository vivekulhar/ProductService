package dev.vivek.productservicetutorial.services;

import dev.vivek.productservicetutorial.dtos.ProductDto;
import dev.vivek.productservicetutorial.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getSingleProduct(Long productId);
    Product addNewProduct(ProductDto product);
    Product updateProduct(Long productId, Product product);
    Product replaceProduct(Long productId, Product product);
    boolean deleteProduct(Long productId);
}
