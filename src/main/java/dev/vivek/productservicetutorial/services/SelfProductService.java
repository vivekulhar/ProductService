package dev.vivek.productservicetutorial.services;

import dev.vivek.productservicetutorial.dtos.ProductDto;
import dev.vivek.productservicetutorial.exceptions.NotFoundException;
import dev.vivek.productservicetutorial.models.Category;
import dev.vivek.productservicetutorial.models.Product;
import dev.vivek.productservicetutorial.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SelfProductService implements ProductService{
    private ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products;
    }

    @Override
    public Optional<Product> getSingleProduct(Long productId) throws NotFoundException {
        return productRepository.findProductById(productId);
    }

    @Override
    public Product addNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Optional<Product> optionalProduct = productRepository.findProductById(productId);
        if(optionalProduct.isPresent()){
            Product product1 = optionalProduct.get();
            product1.setPrice(product.getPrice());
            product1.setImageUrl(product.getImageUrl());
            product1.setCategory(product.getCategory());
            return productRepository.save(product1);
        }
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {

        return updateProduct(productId, product);
    }

    @Override
    @Transactional
    public Integer deleteProduct(Long productId) {

        return productRepository.deleteProductById(productId);
    }
    @Override
    public Product save(Product product){
        return productRepository.save(product);
    }
}
