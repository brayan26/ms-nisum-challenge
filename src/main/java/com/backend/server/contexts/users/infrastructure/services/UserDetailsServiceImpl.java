package com.backend.server.contexts.users.infrastructure.services;

import com.backend.server.contexts.users.domain.clazz.User;
import com.backend.server.contexts.users.infrastructure.impl.UserDetailsImpl;
import com.backend.server.contexts.users.infrastructure.repositories.UserRepositorySql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositorySql repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User Not Found with username: " + email);
        return UserDetailsImpl.create(user);
    }
}
