package joseph.ciaravella.TeeTimeFinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.CustomerAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class CustomerAccountService {
    
    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Transactional
    public void createCustomerAccount(String email, String password) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password field cannot be left blank!");
        }

        CustomerAccount existingCustomer = customerAccountRepository.findByEmail(email).orElse(null);
        if (existingCustomer != null) {
            throw new IllegalArgumentException("this email is already in use!");
        }

        CustomerAccount customer = new CustomerAccount();
        customer.setEmail(email);
        customer.setPassword(password);
        customerAccountRepository.save(customer);
    }

    @Transactional
    public void updateCustomerEmail(String token, String newEmail) {
        CustomerAccount customer = customerAccountRepository.findByToken(token).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with this email!");
        }

        if (newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("New email field cannot be left blank!");
        }

        if (newEmail.equals(customer.getEmail())) {
            throw new IllegalArgumentException("Old email cannot be the same as new email!");           
        }

        customer.setEmail(newEmail);
        customerAccountRepository.save(customer);
    }

    @Transactional
    public void updateCustomerPassword(String token, String newPassword) {
        CustomerAccount customer = customerAccountRepository.findByToken(token).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with this email!");
        }
        
        if (newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password field cannot be left blank!");
        }

        if (newPassword.equals(customer.getPassword())) {
            throw new IllegalArgumentException("Old password cannot be the same as new password!");           
        }

        customer.setEmail(newPassword);
        customerAccountRepository.save(customer);
    }

    @Transactional
    public void deleteCustomerByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CustomerAccount customer = customerAccountRepository.findByEmail(email).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with this email!");
        }

        customerAccountRepository.delete(customer);
    }

    @Transactional
    public void deleteCustomerByToken(String token) {
        if (token.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CustomerAccount customer = customerAccountRepository.findByToken(token).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with this token!");
        }

        customerAccountRepository.delete(customer);
    }

    @Transactional
    public CustomerAccount getCustomerAccountByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CustomerAccount customer = customerAccountRepository.findByEmail(email).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("No customer found with this token!");
        }

        return customer;
    }

    @Transactional
    public List<CustomerAccount> getAllCustomerAccounts() {
        return Utilities.iterableToArrayList(customerAccountRepository.findAll());
    }



}
