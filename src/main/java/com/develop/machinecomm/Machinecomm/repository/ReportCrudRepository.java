package com.develop.machinecomm.Machinecomm.repository;


import com.develop.machinecomm.Machinecomm.model.Logs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ReportCrudRepository extends CrudRepository<Logs, Long> {

    @Query(value = "SELECT TOP (?) * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID ORDER BY L.LOGIN DESC", nativeQuery = true)
    List<Logs> findReportsLimit(Long limit);

    @Query(value = "SELECT * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.LOGIN BETWEEN (?1) AND (?2)" +
            " ORDER BY L.LOGIN DESC", nativeQuery = true)
    List<Logs> findAllReports(String initialDate, String finalDate);

    @Query(value = "SELECT TOP (?3) * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.LOGIN BETWEEN (?1) AND (?2)" +
            " ORDER BY L.LOGIN DESC", nativeQuery = true)
    List<Logs> findAllReportsLimit(String initialDate, String finalDate, Long limit);

    @Query(value = "SELECT * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.USERS = (?3) AND" +
            " L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByUser(String initialDate, String finalDate, Long user);

    @Query(value = "SELECT TOP (?4) * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.USERS = (?3) AND" +
            " L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByUserLimit(String initialDate, String finalDate, Long user, Long limit);

    @Query(value = "SELECT * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.MACHINE = (?3) AND " +
            " L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByMachine(String initialDate, String finalDate, Long machine);

    @Query(value = "SELECT TOP (?4) * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.MACHINE = (?3) AND " +
            " L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByMachineLimit(String initialDate, String finalDate, Long machine, Long limit);

    @Query(value = "SELECT * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.USERS = (?3)" +
            " AND L.MACHINE = (?4) AND L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByUserAndMachine(String initialDate, String finalDate, Long user, Long machine);

    @Query(value = "SELECT TOP (?5) * FROM LOGS AS L INNER JOIN USERS AS U ON U.ID =" +
            " L.USERS INNER JOIN MACHINE AS M ON L.MACHINE = M.ID WHERE L.USERS = (?3)" +
            " AND L.MACHINE = (?4) AND L.LOGIN BETWEEN (?1) AND (?2) ORDER BY L.LOGIN DESC" , nativeQuery = true)
    List<Logs> findReportsByUserAndMachineLimit(String initialDate, String finalDate, Long user, Long machine, Long limit);

    @Query(value = "SELECT * FROM MACHINE AS M INNER JOIN LOGS AS L ON M.ID =" +
            " L.MACHINE INNER JOIN USERS AS U ON U.ID = L.USERS WHERE" +
            " L.STATUS =(?1) ORDER BY  L.LOGIN DESC", nativeQuery = true)
    List<Logs> findByMachineStatus(int status);
}