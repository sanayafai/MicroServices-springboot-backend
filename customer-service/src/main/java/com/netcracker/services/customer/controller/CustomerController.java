
package com.netcracker.services.customer.controller;

import com.netcracker.services.customer.exception.NotFoundException;
import com.netcracker.services.customer.model.Customer;
import com.netcracker.services.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository repository;

    @GetMapping("/")
    public List<Customer> findAll() {
        LOGGER.info("Customer find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) {
        LOGGER.info("Customer find: id={}", id);
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new NotFoundException("Customer not found with id " + id);
        }
    }

    @PostMapping("/")
    public Customer createCustomer(@RequestBody Customer customer) {
        LOGGER.info("createCustomer : ", customer);
        return repository.save(customer);
    }


    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody Customer customerUpdated) {
        LOGGER.info("updateCustomer : id={}", id + " " + customerUpdated);
        return repository.findById(id)
                .map(customer -> {
                    customer.setName(customerUpdated.getName());
                    customer.setEmailAddress(customerUpdated.getEmailAddress());
                    customer.setAddress(customerUpdated.getAddress());
                    return repository.save(customer);
                }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable long id) {
        LOGGER.info("Customer delete: id={}", id);
        return repository.findById(id)
                .map(customer -> {
                    repository.delete(customer);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }
}

