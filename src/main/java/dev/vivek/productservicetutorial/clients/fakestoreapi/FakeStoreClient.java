package dev.vivek.productservicetutorial.clients.fakestoreapi;

import dev.vivek.productservicetutorial.dtos.FakeStoreCategoryDto;
import dev.vivek.productservicetutorial.dtos.ProductDto;
import dev.vivek.productservicetutorial.models.Category;
import dev.vivek.productservicetutorial.models.Product;
import dev.vivek.productservicetutorial.services.ProductConverter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class FakeStoreClient {
    private RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplateBuilder.build();
    }
    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        RestTemplate restTemplate = restTemplateBuilder.build();
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    public List<FakeStoreProductDto> getAllProducts(){
//        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );
        return List.of(l.getBody());
    }
    public Optional<FakeStoreProductDto> getSingleProduct(Long productId){

        ResponseEntity<FakeStoreProductDto> response =  restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
                FakeStoreProductDto.class, productId);

        FakeStoreProductDto productDto = response.getBody();
        if(productDto==null){
            return Optional.empty();
        }

        return Optional.of(productDto);
    }
    public FakeStoreProductDto addNewProduct(ProductDto product){

        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                product,
                FakeStoreProductDto.class
        );
        FakeStoreProductDto productDto = response.getBody();

        return productDto;
    }

    public FakeStoreProductDto updateProduct(Long productId, Product product){
        FakeStoreProductDto fakeStoreProductDto = ProductConverter.convertProductToFakeStoreProductDto(product);

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );

        return fakeStoreProductDtoResponseEntity.getBody();
    }
    public FakeStoreProductDto replaceProduct(Long productId, Product product){
        FakeStoreProductDto fakeStoreProductDto = ProductConverter.convertProductToFakeStoreProductDto(product);

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );
        return fakeStoreProductDtoResponseEntity.getBody();
    }
    public FakeStoreProductDto deleteProduct(Long productId){
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity= requestForEntity(
                HttpMethod.DELETE,
                "https://fakestoreapi.com/products/{id}",
                null,
                FakeStoreProductDto.class,
                productId
        );
        return fakeStoreProductDtoResponseEntity.getBody();
    }

    public List<Category> getAllCategories(){
        ResponseEntity<Category[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                Category[].class
        );
        return List.of(l.getBody());
    }
    public List<FakeStoreProductDto> getProductsInCategory(String categoryName) {
        ResponseEntity<FakeStoreProductDto[]> l = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/{categoryName}",
                FakeStoreProductDto[].class,
                categoryName
        );
        List<FakeStoreProductDto> fakeStoreProductDtos = List.of(l.getBody());

        return fakeStoreProductDtos;
    }


}
