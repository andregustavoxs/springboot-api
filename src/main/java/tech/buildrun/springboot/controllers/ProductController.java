package tech.buildrun.springboot.controllers;

import org.springframework.web.bind.annotation.*;
import tech.buildrun.springboot.ProductRecordDto;
import tech.buildrun.springboot.models.ProductModel;
import tech.buildrun.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controlador REST para gerenciar operações relacionadas a produtos.
 *
 * <p>Esta classe é anotada com @RestController, indicando que é um controlador
 * REST que lida com requisições HTTP e retorna respostas HTTP.</p>
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see ProductRepository
 */
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
            return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);

        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found.");
        }

        var productModel = productModelOptional.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);

        if(productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productRepository.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully");
    }
}
