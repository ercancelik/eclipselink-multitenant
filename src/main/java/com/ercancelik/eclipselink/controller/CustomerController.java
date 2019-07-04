package com.ercancelik.eclipselink.controller;

import com.ercancelik.eclipselink.model.Customer;
import com.ercancelik.eclipselink.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(this.customerService.getAll());
    }

    @PostMapping
    public ResponseEntity<Customer> add(@RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerService.add(customer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> update(@PathVariable Long customerId, @RequestBody Customer customer) {
        return ResponseEntity.ok(this.customerService.update(customerId, customer));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> delete(@PathVariable Long customerId) {
        this.customerService.delete(customerId);
        return ResponseEntity.ok("OK");
    }
}