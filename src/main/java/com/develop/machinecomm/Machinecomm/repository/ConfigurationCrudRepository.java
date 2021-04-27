package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationCrudRepository extends CrudRepository<Configuration, Long> {

@Query(value = "SELECT * FROM CONFIGURATION WHERE ID = ?1" , nativeQuery = true)
    Configuration findConfigById(Long id);

}
