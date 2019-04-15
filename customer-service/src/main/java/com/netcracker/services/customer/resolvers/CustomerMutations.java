package com.netcracker.services.customer.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.netcracker.services.customer.model.Customer;
import com.netcracker.services.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMutations implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMutations.class);

    @Autowired
    CustomerRepository repository;

    public Customer createCustomer(Customer customer) {
        LOGGER.info("Customer add: customer={}", customer);
        return repository.save(customer);
    }

    public boolean deleteCustomer(Long id) {
        LOGGER.info("Customer delete: id={}", id);

        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Customer updateCustomer(Long id, Customer customer) {
        LOGGER.info("Customer update: id={}, customer={}", id, customer);
        Customer updatedCustomer = null;
        if (repository.findById(id).isPresent()) {
            customer.setId(id);
            updatedCustomer = repository.save(customer);
        }
        return updatedCustomer;
    }

}
