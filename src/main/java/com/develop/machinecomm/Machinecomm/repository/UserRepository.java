package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

}