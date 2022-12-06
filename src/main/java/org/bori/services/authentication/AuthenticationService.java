package org.bori.services.authentication;

import org.bori.controllers.handlers.exceptions.model.authentication.AuthenticationRequiredException;
import org.bori.entities.User;
import org.bori.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    public User getCurrentUser(String resource) throws AuthenticationRequiredException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName =
                ((UserDetailsServiceImpl.UserDetailsImpl) auth.getPrincipal()).getUsername();
        Optional<User> optUser =
                userRepository.findByUsername(userName);
        if (optUser.isEmpty()) {
            LOGGER.warn("User tried to access resource without being authenticated");
            throw new AuthenticationRequiredException(resource, userName);
        }
        return optUser.get();
    }
}
