package com.netcracker.services.customer.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.netcracker.services.customer.model.Customer;
import com.netcracker.services.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerQuery implements GraphQLQueryResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerQuery.class);

    @Autowired
    private CustomerRepository repository;

    public List<Customer> customers() {
        LOGGER.info("Customers find");
        return repository.findAll();
    }

    public Optional<Customer> customer(Long id) {
        LOGGER.info("Customer find: id={}", id);
        return repository.findById(id);
    }

}
