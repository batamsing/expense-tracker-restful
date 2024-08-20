package com.batamsingh.expensetracker.utility;

import com.batamsingh.expensetracker.model.Users;
import com.batamsingh.expensetracker.repository.MyUserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserService {

    private final MyUserRepository userRepository;

    public AuthenticatedUserService(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getAuthenticatedUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }
}
