package com.develop.machinecomm.Machinecomm.repository;

import com.develop.machinecomm.Machinecomm.model.Permissions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermissionCrudRepository  extends CrudRepository<Permissions, Integer> {
    @Query(value = "SELECT * FROM PERMISSIONS P INNER JOIN USERS U ON U.ID = " +
            " P.USERS JOIN MACHINE M ON P.MACHINE = M.ID" , nativeQuery = true)
    List<Permissions> findAllPermissions();

    @Query(value = "SELECT * FROM PERMISSIONS P INNER JOIN USERS U ON U.ID = " +
            " P.USERS JOIN MACHINE M ON P.MACHINE = M.ID WHERE U.ID = ?1" , nativeQuery = true)
    List<Permissions> findByUser(Integer userId);

    @Query(value = "SELECT * FROM PERMISSIONS P INNER JOIN USERS U ON U.ID = " +
            " P.USERS JOIN MACHINE M ON P.MACHINE = M.ID WHERE M.ID = ?1" , nativeQuery = true)
    List<Permissions> findByMachine(Long machineId);

    @Query(value = "SELECT * FROM PERMISSIONS P INNER JOIN USERS U ON U.ID = " +
            " P.USERS JOIN MACHINE M ON P.MACHINE = M.ID WHERE U.ID = ?1 AND M.ID = ?2" , nativeQuery = true)
    Permissions findByUserAndMachine(Integer userId, Long machineId);

    @Query(value = "SELECT * FROM PERMISSIONS WHERE ID = ?1" , nativeQuery = true)
    Permissions findByPermissionId(Long permId);
}

