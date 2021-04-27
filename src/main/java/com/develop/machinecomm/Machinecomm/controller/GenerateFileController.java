package com.develop.machinecomm.Machinecomm.controller;

import com.develop.machinecomm.Machinecomm.dto.Report;
import com.develop.machinecomm.Machinecomm.service.ExcelGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class GenerateFileController {

    @Autowired
    ExcelGeneratorService excelGeneratorService;

    // Generate Excel Report File
    @GetMapping("/genexcelall/{initialDate}/{finalDate}/{limit}")
    public List<Report> generateAllReportExcel(@PathVariable(value = "initialDate") String initialDate,
                                               @PathVariable(value = "finalDate") String finalDate,
                                               @PathVariable(value = "limit") long limit) {
       return excelGeneratorService.generateAllReportExcel(initialDate, finalDate, limit);
    }

    @GetMapping("/genexceluser/{initialDate}/{finalDate}/{userId}/{limit}")
    public List<Report> generateExcelByUser(@PathVariable(value = "initialDate") String initialDate,
                                            @PathVariable(value = "finalDate") String finalDate,
                                            @PathVariable(value = "userId") long userId,
                                            @PathVariable(value = "limit") long limit) {
        return excelGeneratorService.generateExcelByUser(initialDate, finalDate, userId, limit);
    }

    @GetMapping("/genexcelmac/{initialDate}/{finalDate}/{macId}/{limit}")
    public List<Report> generateExcelByMachine(@PathVariable(value = "initialDate") String initialDate,
                                               @PathVariable(value = "finalDate") String finalDate,
                                               @PathVariable(value = "macId") long macId,
                                               @PathVariable(value = "limit") long limit) {
        return excelGeneratorService.generateExcelByMachine(initialDate, finalDate, macId, limit);
    }

    @GetMapping("/genexcelusermac/{initialDate}/{finalDate}/{userId}/{macId}/{limit}")
    public List<Report> generateExcelByUserAndMachine(@PathVariable(value = "initialDate") String initialDate,
                                                      @PathVariable(value = "finalDate") String finalDate,
                                                      @PathVariable(value = "userId") long userId,
                                                      @PathVariable(value = "macId") long macId,
                                                      @PathVariable(value = "limit") long limit) {
        return excelGeneratorService.generateExcelByUserAndMachine(initialDate, finalDate, userId, macId, limit);
    }
}