package com.develop.machinecomm.Machinecomm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class APITest {

    @PostMapping("/deploy")
    public ResponseEntity<?> createUser(HttpServletRequest request) {
        return null;
    }
}
