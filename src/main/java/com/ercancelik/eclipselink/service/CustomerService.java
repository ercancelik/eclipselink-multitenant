package com.ercancelik.eclipselink.service;

import com.ercancelik.eclipselink.config.TenantContext;
import com.ercancelik.eclipselink.model.Customer;
import com.ercancelik.eclipselink.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.config.EntityManagerProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {
    @PersistenceContext
    EntityManager entityManager;

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer add(Customer customer) {
        setTenant();
        return this.customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Long customerId, Customer customer) {
        setTenant();

        Optional<Customer> temp = this.customerRepository.findById(customerId);
        if (temp.isPresent()) {
            temp.get().setFirstName(customer.getFirstName());
            temp.get().setLastName(customer.getLastName());
            return temp.get();
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<Customer> getAll() {
        setTenant();

        return this.customerRepository.findAll();
    }

    @Transactional
    public void delete(Long customerId) {
        setTenant();

        Optional<Customer> temp = this.customerRepository.findById(customerId);
        if (temp.isPresent()) {
            this.customerRepository.delete(temp.get());
        }
    }

    private void setTenant(){
        log.info("Current tenant : {}", TenantContext.getCurrentTenant());
        this.entityManager.setProperty(EntityManagerProperties.MULTITENANT_PROPERTY_DEFAULT, TenantContext.getCurrentTenant());
    }
}
