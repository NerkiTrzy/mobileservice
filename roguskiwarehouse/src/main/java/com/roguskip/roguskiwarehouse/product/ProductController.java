package com.roguskip.roguskiwarehouse.product;

import com.roguskip.roguskiwarehouse.exceptions.ResourceNotFoundException;
import com.roguskip.roguskiwarehouse.manufacturer.Manufacturer;
import com.roguskip.roguskiwarehouse.manufacturer.ManufacturerRepository;
import com.roguskip.roguskiwarehouse.warehouse.Warehouse;
import com.roguskip.roguskiwarehouse.warehouse.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {

    private ProductRepository productRepository;
    private ManufacturerRepository manufacturerRepository;
    private WarehouseRepository warehouseRepository;

    @GetMapping("/products")
    public List<?> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @PostMapping("/manufacturers/{manufacturerId}/products")
    //@PreAuthorize("hasRole('USER')")
    public Product createProduct(@PathVariable(name = "manufacturerId") Long manufacturerId,
                                 @Valid @RequestBody ProductAddView productAddView) {

        Product product = new Product();
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", "Id", manufacturerId));

        if (productAddView.getWarehouseId() == null)
            productAddView.setWarehouseId(0L);

        Warehouse warehouse = warehouseRepository.findById(productAddView.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "Id", productAddView.getWarehouseId()));

        product.setId(null);
        product.setManufacturer(manufacturer);
        product.setQuantity(0);
        product.setColor(productAddView.getColor());
        product.setCurrency(productAddView.getCurrency());
        product.setPrice(productAddView.getPrice());
        product.setWarehouse(warehouse);
        product.setName(productAddView.getName());
        return productRepository.save(product);
    }

    @PostMapping(path = "/products/{productUuid}/change-quantity")
   // @PreAuthorize("hasRole('USER')")
    public Product changeQuantity(@PathVariable(value = "productUuid") UUID productUuid,
                                    @Valid @RequestBody Integer quantity) {

        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productUuid", productUuid));

        product.setQuantity(product.getQuantity() + quantity);

        return  productRepository.save(product);
    }

    @DeleteMapping(path = "/products/{productUuid}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "productUuid") UUID productUuid) {
        Product product = productRepository.findByUuid(productUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productUuid", productUuid));

        productRepository.delete(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
