package dev.vivek.productservicetutorial;

import dev.vivek.productservicetutorial.models.Category;
import dev.vivek.productservicetutorial.models.Product;
import dev.vivek.productservicetutorial.repositories.CategoryRepository;
import dev.vivek.productservicetutorial.repositories.product.ProductRepository;
import dev.vivek.productservicetutorial.services.SelfProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private SelfProductService selfProductService;
    @Test
    void savingProductsAndCategory() {
//        Category category = new Category();
//        category.setName("phones");
//        Category savedCategory = categoryRepository.save(category);
//
//        Product product = new Product();
//        product.setPrice(100);
//        product.setImageUrl("hello");
//        product.setCategory(category);
//        productRepository.save(product);

        Category category = new Category();
        category.setName("electronics");
//        Category savedCategory = categoryRepository.save(category);

        Product product = new Product();
        product.setPrice(101.0);
        product.setImageUrl("hiii");
        product.setCategory(category);
        productRepository.save(product);
    }

    @Test
    @Transactional
    void fetchTypesTest() {
        Optional<Product> product = productRepository.findProductById(1L);

        System.out.println("Fetched product");

        Category category = product.get().getCategory();
        String name = category.getName();
    }

    @Test
    void deleteProduct() {
        productRepository.deleteById(1L);


    }

    @Test
    @Transactional
//    @Rollback(value = false)
    @Commit()
    void saveProductsForCategory() {
        Category category1 = new Category();
        category1.setName("phones");
        categoryRepository.save(category1);
        Category category2 = new Category();
        category2.setName("electronics");
        categoryRepository.save(category2);
        Category category = categoryRepository.findById(2L).get();


        Product product = new Product();
        product.setPrice(1012.0);
        product.setImageUrl("hiii");
        product.setCategory(category2);
        Product savedProduct = productRepository.save(product);

        product = new Product();
        product.setPrice(103.0);
        product.setImageUrl("hiii");
        product.setCategory(category);
        productRepository.save(product);

        Category category3 = new Category();
        category3.setName("hello");
        categoryRepository.save(category3);

    }

    @Test
    @Transactional
    void getProductsForCategory() throws InterruptedException {
//        Category category = categoryRepository.findById(2L).get();

        Optional<List<Category>> categories = categoryRepository.findAllByIdIn(List.of(2L, 52L)); // select * from categories where cateory in 1, 2

        if(categories.isEmpty()) {
            System.out.println("No categories found");
            return;
        }
        // select * from categories where id in (2, 52);
        Thread.sleep(100L);
        System.out.println("Doing something");
        Thread.sleep(100L);
        // select * from products where category_id = 2;
        // select * from products where category_id = 52;

        for (Category category: categories.get()) {
            for (Product product : category.getProducts()) {
                System.out.println(product.getPrice());
            }
        }
    }

    @Test
    @Transactional
    void getProductsFor1Category() throws InterruptedException {
//        Category category = categoryRepository.findById(2L).get();

        Category category = categoryRepository.findById(2L).get(); // select * from categories where cateory in 1, 2

        // select * from categories where id in (2, 52);
        Thread.sleep(100L);
        System.out.println("Doing something");
        Thread.sleep(100L);
        // select * from products where category_id = 2;
        // select * from products where category_id = 52;

        for (Product product : category.getProducts()) {
            System.out.println(product.getPrice());
        }
    }

    @Test
    void checkWorkingFine() {


    }
}
