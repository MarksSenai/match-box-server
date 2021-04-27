package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Logs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogsCrudRepository extends CrudRepository<Logs, Long> {

    @Query(value = "SELECT * FROM LOGS WHERE USERS = ?1 AND machine = ?2 and status = ?3" , nativeQuery = true)
    Logs findByUserAndMachineAndLogout(Integer user, Long machine, int status);

    @Query(value = "SELECT * FROM LOGS WHERE USERS = ?1 AND status = ?2" , nativeQuery = true)
    List<Logs> findByUserAndStatus(Long user, int status);

    @Query(value = "SELECT * FROM LOGS WHERE USERS = ?1 AND status = ?2" , nativeQuery = true)
    Logs findByUserLogged(Integer user, int status);

    @Query(value = "SELECT * FROM LOGS WHERE machine = ?1 AND status = ?2" , nativeQuery = true)
    Logs checkLogMachine(Long machine, int status);

    @Query(value = "SELECT * FROM LOGS WHERE machine = ?1 AND status = ?2" , nativeQuery = true)
    Logs checkUserAndMachine(Long machine, int status);
}
