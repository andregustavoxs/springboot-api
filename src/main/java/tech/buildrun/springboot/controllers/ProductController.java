package tech.buildrun.springboot.controllers;

import org.springframework.web.bind.annotation.*;
import tech.buildrun.springboot.dtos.ProductRecordDto;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    /**
     * Salva um novo produto.
     *
     * @param productRecordDto o DTO do produto que contém os dados a serem salvos, não pode ser nulo ou inválido
     * @return ResponseEntity contendo o modelo do produto salvo e o status HTTP 201 (Created)
     */
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    /**
     * Retorna todos os produtos.
     *
     * @return ResponseEntity contendo a lista de todos os modelos de produtos e o status HTTP 200 (OK)
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if(!productsList.isEmpty()) {
            for(ProductModel productModel : productsList) {
                UUID id = productModel.getIdProduct();
                productModel.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    /**
     * Retorna um produto específico pelo ID.
     *
     * @param id o UUID do produto a ser retornado, não pode ser nulo
     * @return ResponseEntity contendo o modelo do produto encontrado e o status HTTP 200 (OK), ou uma mensagem de erro e o status HTTP 404 (Not Found) se o produto não for encontrado
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productModelOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    /**
     * Atualiza um produto existente pelo ID.
     *
     * @param id               o UUID do produto a ser atualizado, não pode ser nulo
     * @param productRecordDto o DTO do produto que contém os novos dados, não pode ser nulo ou inválido
     * @return ResponseEntity contendo o modelo do produto atualizado e o status HTTP 200 (OK), ou uma mensagem de erro e o status HTTP 404 (Not Found) se o produto não for encontrado
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);

        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found.");
        }

        var productModel = productModelOptional.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    /**
     * Deleta um produto existente pelo ID.
     *
     * @param id o UUID do produto a ser deletado, não pode ser nulo
     * @return ResponseEntity contendo uma mensagem de sucesso e o status HTTP 200 (OK), ou uma mensagem de erro e o status HTTP 404 (Not Found) se o produto não for encontrado
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productModelOptional = productRepository.findById(id);

        if (productModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productRepository.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully");
    }
}
