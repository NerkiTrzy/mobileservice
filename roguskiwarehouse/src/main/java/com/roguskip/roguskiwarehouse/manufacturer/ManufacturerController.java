package com.roguskip.roguskiwarehouse.manufacturer;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ManufacturerController {

    ManufacturerRepository manufacturerRepository;

    @PostMapping("/manufacturers")
    public Manufacturer createManufacturer(@Valid @RequestBody Manufacturer manufacturer) {
        manufacturer.setId(null);
        return manufacturerRepository.save(manufacturer);
    }

    @GetMapping("/manufacturers")
    public List<Manufacturer> getManufacturers() {
        return manufacturerRepository.findAll(Sort.by("name"));
    }
}
