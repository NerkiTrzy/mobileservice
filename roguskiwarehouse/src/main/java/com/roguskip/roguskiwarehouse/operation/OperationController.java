package com.roguskip.roguskiwarehouse.operation;

import com.roguskip.roguskiwarehouse.exceptions.ResourceNotFoundException;
import com.roguskip.roguskiwarehouse.manufacturer.Manufacturer;
import com.roguskip.roguskiwarehouse.manufacturer.ManufacturerRepository;
import com.roguskip.roguskiwarehouse.model.OperationName;
import com.roguskip.roguskiwarehouse.model.ProductView;
import com.roguskip.roguskiwarehouse.product.Product;
import com.roguskip.roguskiwarehouse.product.ProductRepository;
import com.roguskip.roguskiwarehouse.warehouse.Warehouse;
import com.roguskip.roguskiwarehouse.warehouse.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OperationController {

    private ProductRepository productRepository;
    private ManufacturerRepository manufacturerRepository;
    private OperationRepository operationRepository;
    private WarehouseRepository warehouseRepository;

    @PostMapping(path = "operations/do-operations")
    public List<?> doOperations(@Valid @RequestBody ArrayList<Operation> operationList) {

        operationList.forEach(operation -> {
            Operation o = operationRepository.findByUuid(operation.getUuid())
                    .orElse(null);

            if (o == null) {
                executeOperation(operation);
                operationRepository.save(operation);
            }
        });
        return productRepository.getAllProducts();
    }

    private void executeOperation(Operation operation) {
        switch (OperationName.valueOf(operation.getOperationName())) {
            case INSERT:
                insertProduct(operation.getProductView());
                break;
            case UPDATE:
                updateProductQuantity(operation.getProductView(), operation.getQuantity());
                break;
            case DELETE:
                deleteProduct(operation.getProductView());
                break;
            default:
                break;
        }
    }

    private void deleteProduct(ProductView productView) {
        Product product = productRepository.findByUuid(UUID.fromString(productView.getProductGUID()))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productUuid", productView.getProductGUID()));

        productRepository.delete(product);
    }

    private void updateProductQuantity(ProductView productView, Integer quantity) {
        Product product = productRepository.findByUuid(UUID.fromString(productView.getProductGUID()))
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productUuid", productView.getProductGUID()));

        product.setQuantity(product.getQuantity() + quantity);

        productRepository.save(product);
    }

    private void insertProduct(ProductView productView) {
        Product product = new Product();
        Manufacturer manufacturer = manufacturerRepository.findById(productView.getManufacturerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", "Id", productView.getManufacturerId()));

        if (productView.getWarehouseId() == null)
            productView.setWarehouseId(0L);

        Warehouse warehouse = warehouseRepository.findById(productView.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "Id", productView.getWarehouseId()));

        if (productView.getColor() == null)
            productView.setColor("Not Specified");
        product.setId(null);
        product.setUuid(UUID.fromString(productView.getProductGUID()));
        product.setManufacturer(manufacturer);
        product.setQuantity(0);
        product.setName(productView.getProductName());
        product.setPrice(productView.getPrice());
        product.setCurrency(Currency.getInstance("USD"));
        product.setColor(productView.getColor());
        product.setWarehouse(warehouse);
        productRepository.save(product);

    }
}
