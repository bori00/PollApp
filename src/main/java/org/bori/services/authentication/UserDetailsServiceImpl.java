package org.bori.services.authentication;

import org.bori.entities.User;
import org.bori.repositories.basic.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A Service which allows SPring Security to find a user by their username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optAdmin = userRepository.findByUsername(username);
        if (optAdmin.isPresent()) {
            return new UserDetailsImpl(optAdmin.get());
        }
        throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

    /**
     * A class holding the details of a user in the format requested by SPring Security.
     */
    public static class UserDetailsImpl implements UserDetails {

        private final User user;
        private final Collection<GrantedAuthority> authorities;

        public enum Authorities {
            GENERAL
        }

        public UserDetailsImpl(User user) {
            this.user = user;
            this.authorities = List.of(new SimpleGrantedAuthority(Authorities.GENERAL.toString()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getPassword() {
            return user.getPassword();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getUsername() {
            return user.getUsername();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
