package com.develop.machinecomm.Machinecomm.security;

import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserCrudRepository userRepository;
    Users user;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userRepository.findByUserEmail(userName) != null) {
            user = userRepository.findByUserEmail(userName);
            return UserPrincipal.create(user);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o email : " + userName);
        }
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(Integer id) {
        if (userRepository.findByUserId(String.valueOf(id)) != null) {
            user = userRepository.findByUserId(String.valueOf(id));
            return UserPrincipal.create(user);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado com o id : " + id);
        }

    }
}
