package com.roguskip.roguskiwarehouse.warehouse;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WarehouseController {

    private WarehouseRepository warehouseRepository;

    @GetMapping("/warehouses")
    public List<Warehouse> getAllWarehouses(){return warehouseRepository.findAll(Sort.by("name"));}
}
