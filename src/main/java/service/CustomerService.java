package service;

import model.Customer;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;


public class CustomerService {
    private static CustomerService customerservice;
    private Set<Customer> customers;
    private CustomerService () { customers = new HashSet<Customer>(); }

    public static CustomerService getInstance() {
        if (customerservice == null) {
            customerservice = new CustomerService();
        }
        return customerservice;
    }

    public void addCustomer(String email, String firstName, String lastName) throws Exception {
        Customer potentialCustomer = new Customer(firstName, lastName, email);
        if (!customers.contains(potentialCustomer)) {
            customers.add(potentialCustomer);
        } else {
            throw new Exception("Customer with same email in dataset.");
        }
    }

    public Optional<Customer> getCustomer(String customerEmail) {
        Customer foundCustomer = null;
        for (Customer c : customers) {
            if (c.getEmail().equals(customerEmail)) foundCustomer = c;
        }
        return Optional.ofNullable(foundCustomer);
    }

    public Collection<Customer> getAllCustomers() {
        return (Collection<Customer>) customers;
    }
}
