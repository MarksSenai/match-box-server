package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.dto.Report;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelGeneratorService {

    private ReportService reportService;
    private ConfigurationService configurationService;

    @Autowired
    public ExcelGeneratorService(ReportService reportService,
                                 ConfigurationService configurationService){
        this.reportService = reportService;
        this.configurationService = configurationService;
    }

    public List<Report> generateAllReportExcel(String initialDate, String finalDate, Long limit){
       genSpreadsheet((ArrayList<Report>) reportService.findAllReports(initialDate, finalDate, limit));
       return null;
    }

    public List<Report> generateExcelByUser(String initialDate, String finalDate, Long userId, Long limit){
        genSpreadsheet((ArrayList<Report>) reportService.findReportsByUser(initialDate, finalDate, userId, limit));
        return null;
    }

    public List<Report> generateExcelByMachine(String initialDate, String finalDate, Long macId, Long limit){
        genSpreadsheet((ArrayList<Report>) reportService.findReportsByMachine(initialDate, finalDate, macId, limit));
        return null;
    }

    public List<Report> generateExcelByUserAndMachine(String initialDate, String finalDate, Long userId, Long macId, Long limit){
        genSpreadsheet((ArrayList<Report>) reportService.findReportsByUserAndMachine(initialDate, finalDate, userId, macId, limit));
        return null;
    }

    private void genSpreadsheet(ArrayList<Report> reports){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("relatorio");

        XSSFRow row = spreadsheet.createRow(1);

        XSSFCell cell;

        cell = row.createCell(1);
        cell.setCellValue("Maquina");
        cell = row.createCell(2);
        cell.setCellValue("Operador");
        cell = row.createCell(3);
        cell.setCellValue("Matricula");
        cell = row.createCell(4);
        cell.setCellValue("Inicio");
        cell = row.createCell(5);
        cell.setCellValue("Fim");
        cell = row.createCell(6);
        cell.setCellValue("Data");
         int i = 2;

        for (Report report : reports) {

            row = spreadsheet.createRow(i);

            cell = row.createCell(1);
            cell.setCellValue(report.getMachine());

            cell = row.createCell(2);
            cell.setCellValue(report.getUser());

            cell = row.createCell(3);
            cell.setCellValue(report.getRec());

            cell = row.createCell(4);
            cell.setCellValue(report.getLogin());

            if(report.getLogout() != null) {
                cell = row.createCell(5);
                cell.setCellValue(report.getLogout());
            } else {
                cell = row.createCell(5);
                cell.setCellValue("Em operação");
            }

            cell = row.createCell(6);
            cell.setCellValue(report.getLoginDate());

            i +=1;
        }
        saveFile(workbook);
    }

    private void saveFile(XSSFWorkbook workbook) {

        FileOutputStream out = null;
        File file;
        String path = this.configurationService.getConfig().getVolume();
        path += "/ExcelFile";
        //path += "/ExcelFile/excelfilereport.xlsx";
        try {
            file = new File(path);
            file.mkdirs(); // Create ExcelFile folde, if do not exist.
            path += "/excelfilereport.xlsx";
            out = new FileOutputStream(new File(path));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}