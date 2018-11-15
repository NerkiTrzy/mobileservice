package com.roguskip.roguskiwarehouse.product;

import com.roguskip.roguskiwarehouse.exceptions.ResourceNotFoundException;
import com.roguskip.roguskiwarehouse.manufacturer.Manufacturer;
import com.roguskip.roguskiwarehouse.manufacturer.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {

    private ProductRepository productRepository;
    private ManufacturerRepository manufacturerRepository;

    @GetMapping("/products")
    public List<?> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @PostMapping("/manufacturers/{manufacturerId}/products")
    public Product createProduct(@PathVariable(name = "manufacturerId") Long manufacturerId,
                                 @Valid @RequestBody Product product) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", "Id", manufacturerId));

        product.setId(null);
        product.setManufacturer(manufacturer);
        product.setQuantity(0);
        return productRepository.save(product);
    }

    @PutMapping(path = "/products/{productId}/increase-quantity", params ={"quantity"})
    public Product increaseQuantity(@PathVariable(value = "productId") Long productId,
                                    @RequestParam(value = "quantity") Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        product.setQuantity(product.getQuantity() + quantity);

        return  productRepository.save(product);
    }

    @PutMapping(path = "/products/{productId}/decrease-quantity", params ={"quantity"})
    public Product decreaseQuantity(@PathVariable(value = "productId") Long productId,
                                    @RequestParam(value = "quantity") Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        product.setQuantity(product.getQuantity() - quantity);

        return  productRepository.save(product);
    }
}
