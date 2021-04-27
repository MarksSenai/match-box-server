package com.develop.machinecomm.Machinecomm.service;



import com.develop.machinecomm.Machinecomm.exception.UserAlreadyExistsException;
import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.repository.UserCrudRepository;
import com.develop.machinecomm.Machinecomm.repository.UserRepository;
import com.develop.machinecomm.Machinecomm.security.EncodDecodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


@Service
public class UserService extends UserAlreadyExistsException {

    private UserCrudRepository userCrudRepository;
    private UserRepository userRepository;
    private ReportService reportService;
    private EncodDecodService encodDecodService;
    private PasswordEncoder passwordEncoder;
    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserCrudRepository userCrudRepository,
                       UserRepository userRepository,
                       ReportService reportService,
                       EncodDecodService encodDecodService,
                       PasswordEncoder passwordEncoder) {
        super();
        this.userCrudRepository = userCrudRepository;
        this.userRepository = userRepository;
        this.reportService = reportService;
        this.encodDecodService = encodDecodService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> createUser(Users user) {
        Users rfid = findByRFID(user.getRfid());
        Users record = findByUserByRecord(user.getReg());
        if (rfid == null && record == null) {
        try{
            Users userResponse;
            user.setPassword(encodDecodService.decodeBase64(user.getPassword()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));

                userResponse = this.userRepository.save(user);

                return ResponseEntity.ok().body(userResponse);

        }catch(UserAlreadyExistsException e){
            throw new UserAlreadyExistsException("Falha na criação do usuário "+user.getName());
        }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A matrícula "+user.getReg()+" ou o RFID "
                    +user.getRfid()+" já se encontra em uso");
        }
    }

    public List<Users> findAllUsers() {

        List<Users> users = this.userCrudRepository.findAllUsers("matchbox");
        return users;
    }

    public Users findByRFID(String rfid) {
        Users user = this.userCrudRepository.findByUserRfid(rfid);
        return user;
    }

    public Users findById(Integer id) {
        Users user = userCrudRepository.findByUserId(String.valueOf(id));
        user.setPassword("");
        return user;
    }

    public Users findByUserByRecord(String rec) {
        Users user = userCrudRepository.findByUserRecord(rec);
        return user;
    }

    public List<Users> searchUser(String userParam){
        List<Users> users = userCrudRepository.searchUser(userParam);
        return  users;
    }

    public ResponseEntity<?> updateUser (Users user, Integer perfil){
        if (user.getPassword() == "") {
            user.setPassword(userCrudRepository.findByUserId(String.valueOf(user.getId())).getPassword());
            Users updatedUser = userRepository.save(user);
            updatedUser.setPassword("");
            return ResponseEntity.ok().body(updatedUser);
        }
        user.setPassword(encodDecodService.decodeBase64(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPerfis(Collections.singleton(perfil));

        Users updatedUser = userRepository.save(user);
        updatedUser.setPassword("");
        return ResponseEntity.ok().body(updatedUser);
    }

    public Users updateUserImage (String userCode, String fileName) {
        Users user = findByUserByRecord(userCode);
        user.setPicture(fileName);

        return  this.userRepository.save(user);
    }

}