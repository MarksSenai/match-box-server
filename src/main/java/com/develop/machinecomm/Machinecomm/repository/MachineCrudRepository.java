package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Machine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MachineCrudRepository extends CrudRepository<Machine, Long> {
    Machine findByMacidIgnoreCase(String macid);

    @Query(value = "SELECT * FROM MACHINE ORDER BY MACID" , nativeQuery = true)
    List<Machine> findAllMachines();

    @Query(value = "SELECT USERLOGGED FROM MACHINE WHERE macId = ?1" , nativeQuery = true)
    String findUserLogged(String macId);

    @Query(value = "SELECT * FROM MACHINE WHERE ID = ?1" , nativeQuery = true)
    Machine findMachineById(Long id);

    @Query(value = "SELECT * FROM MACHINE WHERE DESCRIPTION LIKE %:machine% OR MACID LIKE %:machine%" +
            "ORDER BY MACID", nativeQuery = true)
    List<Machine> searchMachine(String machine);
}
