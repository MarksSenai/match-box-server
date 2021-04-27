package com.develop.machinecomm.Machinecomm.controller;

import com.develop.machinecomm.Machinecomm.exception.ResourceNotFoundException;
import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.repository.UserCrudRepository;
import com.develop.machinecomm.Machinecomm.repository.UserRepository;
import com.develop.machinecomm.Machinecomm.service.ImportService;
import com.develop.machinecomm.Machinecomm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class UserController {

    private UserRepository userRepository;
    private UserCrudRepository userCrudRepository;
    private UserService userService;
    private ImportService importService;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          UserCrudRepository userCrudRepository,
                          ImportService importService){
        this.userRepository = userRepository;
        this.userService = userService;
        this.userCrudRepository = userCrudRepository;
        this.importService = importService;
    }


    // Create a new User
    @PostMapping("/user/{perfil}")
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user, @PathVariable(value = "perfil") Integer perfil) {
        user.setPerfis(Collections.singleton(perfil));
        return this.userService.createUser(user);
    }

    // Get All User
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return this.userService.findAllUsers();
    }


    @GetMapping("/userimport")
    public boolean importUsers() throws IOException {
        this.importService.createUsers();
        return true;
    }


    // Get a Single User
    @GetMapping("/user/{userId}")
    public Users getUserById(@PathVariable(value = "userId") Integer userId) {
        return userService.findById(userId);

    }

    // Get a Single User
    @GetMapping("/searchuser/{user}")
    public List<Users> searchUser(@PathVariable(value = "user") String user) {
        List<Users> users = userService.searchUser(user);

        return users;
    }

    // Get a Single User by RFID
    @GetMapping("/user/rfid/{rfid}")
    public Users getUserByRFID(@PathVariable(value = "rfid") int rfId) {
        return userRepository.findById(rfId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", rfId));
    }

    // Update a User
    @PutMapping("/user/{perfil}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Users user, @PathVariable(value = "perfil") Integer perfil ) {
        return userService.updateUser(user, perfil);
    }
}