package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserCrudRepository extends CrudRepository<Users, String> {

   @Query(value = "SELECT * FROM USERS WHERE rfid = ?1 ORDER BY NAME" , nativeQuery = true)
   Users findByUserRfid(String rfid);

   @Query(value = "SELECT * FROM USERS WHERE EMAIL = ?1 OR REG = ?1 ORDER BY NAME" , nativeQuery = true)
   Users findByUserEmail(String email);

   @Query(value = "SELECT * FROM USERS WHERE REG = ?1 ORDER BY NAME" , nativeQuery = true)
   Users findByUserRecord(String reg);

   @Query(value = "SELECT * FROM USERS WHERE REG <> ?1 ORDER BY NAME" , nativeQuery = true)
   List<Users> findAllUsers(String matchbox);

   @Query(value = "SELECT * FROM USERS WHERE id = ?1 ORDER BY NAME" , nativeQuery = true)
   Users findByUserId(String id);

   @Query(value = "SELECT * FROM USERS WHERE NAME LIKE %:user% OR REG LIKE %:user% OR RFID LIKE %:user% ORDER BY NAME" , nativeQuery = true)
   List<Users> searchUser(String user);

   @Query(value = "SELECT MACLOGGED FROM USERS WHERE rfid = ?1" , nativeQuery = true)
   String findMacLogged(String rfid);

}

