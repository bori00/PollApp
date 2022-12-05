package org.bori.services;

import org.bori.entities.User;
import org.bori.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    TestService(UserRepository repository) {

        // save a couple of customers
        repository.save(new User("Alice", "passA"));
        repository.save(new User("Bob", "passB"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();
    }
}
