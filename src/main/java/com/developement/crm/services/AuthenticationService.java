package com.developement.crm.services;

import com.developement.crm.exceptionHandlers.NoUserFindOnSession;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByLogin(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserModel getUserBySession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Optional<UserModel> userOpt = userRepository.findUserModelByEmail(user.getUsername());
        if(userOpt.isPresent()){
            return userOpt.get();
        }else {
            throw new NoUserFindOnSession("User not found user in this session: " + authentication.getName());
        }
    }
}
