package com.develop.machinecomm.Machinecomm.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EncodDecodService {

    public String decodeBase64(String password) {
        byte[] byteArray = Base64.decodeBase64(password.getBytes());
        return new String(byteArray);
    }

    public String encodeBase64(String password) {
        return null;
    }

    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
