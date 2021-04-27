package com.develop.machinecomm.Machinecomm.controller;


import com.develop.machinecomm.Machinecomm.dto.Report;
import com.develop.machinecomm.Machinecomm.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class ReportController {

    @Autowired
    ReportService reportService;

    // Get Reports only by limit
    @GetMapping("report/{limit}")
    public List<Report> findReports(@PathVariable(value = "limit") Long limit) {
        return reportService.findReports(limit);
    }

    // Get All Reports
    @GetMapping("report/{initialDate}/{finalDate}/{limit}")
    public List<Report> findAllReports(@PathVariable(value = "limit") Long limit,
                                      @PathVariable(value = "initialDate")String initialDate,
                                      @PathVariable(value = "finalDate")String finalDate) {
        return reportService.findAllReports(initialDate, finalDate, limit);
    }

    // Get Reports by User
    @GetMapping("/report/user/{userId}/{initialDate}/{finalDate}/{limit}")
    public List<Report> findReportsByUser(@PathVariable(value = "userId") Long userId,
                                          @PathVariable(value = "limit") Long limit,
                                          @PathVariable(value = "initialDate")String initialDate,
                                          @PathVariable(value = "finalDate")String finalDate) {
        return reportService.findReportsByUser(initialDate, finalDate, userId, limit);
    }

    // Get Reports by Machine
    @GetMapping("/report/machine/{macId}/{initialDate}/{finalDate}/{limit}")
    public List<Report> findReportsByMachine(@PathVariable(value = "macId") Long macId,
                                             @PathVariable(value = "limit") Long limit,
                                             @PathVariable(value = "initialDate")String initialDate,
                                             @PathVariable(value = "finalDate")String finalDate) {
        return reportService.findReportsByMachine(initialDate, finalDate, macId, limit);
    }

    // Get Machine situation
    @GetMapping("/report/machines")
    public List<Report> findByMachineStatus() {
        return reportService.findByMachineStatus();
    }

    // Get Reports by User and Machine
    @GetMapping("/report/usermachine/{userId}/{macId}/{initialDate}/{finalDate}/{limit}")
    public List<Report> findReportsByUserMachine(@PathVariable(value = "userId") Long userId,
                                                 @PathVariable(value = "macId") Long macId,
                                                 @PathVariable(value = "limit") Long limit,
                                                 @PathVariable(value = "initialDate")String initialDate,
                                                 @PathVariable(value = "finalDate")String finalDate) {
        return reportService.findReportsByUserAndMachine(initialDate, finalDate, userId, macId, limit);
    }
}