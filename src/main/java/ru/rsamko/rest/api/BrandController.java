package ru.rsamko.rest.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rsamko.rest.models.*;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
        this.fillTable();
    }

    private void fillTable() {
        String[] brands = { "BMW", "Mercedes-Benz", "Audi" };
        for (String brand: brands) {
            Brand newBrand = new Brand();
            newBrand.brand = brand;
            this.brandRepository.save(newBrand);
        }
    }

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity<Iterable<Brand>>(brandRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        Brand brand = brandRepository.findOne(id);
        if(brand != null) {
            return new ResponseEntity<Brand>(brand, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Brand not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity add(@RequestParam String brand) {
        Brand newBrand = new Brand();
        newBrand.brand = brand;
        brandRepository.save(newBrand);
        return new ResponseEntity<Brand>(newBrand, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Long id, @RequestParam String brand) {
        Brand editableBrand = brandRepository.findOne(id);
        if(editableBrand != null) {
            editableBrand.brand = brand;
            brandRepository.save(editableBrand);
            return new ResponseEntity<Brand>(editableBrand, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Brand not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            brandRepository.delete(id);
            return new ResponseEntity<String>("Brand has been deleted", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<String>("Brand not found", HttpStatus.NOT_FOUND);
        } catch (Error e) {
            return new ResponseEntity<String>("Unknown message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}