package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.dto.Report;
import com.develop.machinecomm.Machinecomm.model.Logs;
import com.develop.machinecomm.Machinecomm.repository.LogsRepository;
import com.develop.machinecomm.Machinecomm.repository.ReportCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private LogsRepository logsRepository;
    private ReportCrudRepository reportCrudRepository;

    @Autowired
    public ReportService(LogsRepository logsRepository, ReportCrudRepository reportCrudRepository) {
        this.logsRepository = logsRepository;
        this.reportCrudRepository = reportCrudRepository;
    }

    public List<Report> findReports(Long limit) {
        return reportListGenerate(reportCrudRepository
                .findReportsLimit(limit));
    }

    public List<Report> findAllReports(String initialDate, String finalDate, Long limit) {

        if (limit == 0) {
            return reportListGenerate(reportCrudRepository
                    .findAllReports(initialDate, getFinalDate(finalDate)));
        } else {
            return reportListGenerate(reportCrudRepository
                    .findAllReportsLimit(initialDate, getFinalDate(finalDate), limit));
        }
    }

    public List<Report> findReportsByUser(String initialDate, String finalDate, Long userId, Long limit){
        if (limit == 0) {
            return reportListGenerate(reportCrudRepository
                    .findReportsByUser(initialDate, getFinalDate(finalDate), userId));
        } else {
            return reportListGenerate(reportCrudRepository
                    .findReportsByUserLimit(initialDate, getFinalDate(finalDate), userId, limit));
        }
    }

    public List<Report> findReportsByMachine(String initialDate, String finalDate, Long macId, Long limit){
        if (limit == 0) {
            return reportListGenerate(reportCrudRepository
                    .findReportsByMachine(initialDate, getFinalDate(finalDate), macId));
        } else {
            return reportListGenerate(reportCrudRepository
                    .findReportsByMachineLimit(initialDate, getFinalDate(finalDate), macId, limit));
        }
    }

    public List<Report> findReportsByUserAndMachine(String initialDate, String finalDate, Long userId, Long macId, Long limit){
        if (limit == 0) {
            return reportListGenerate(reportCrudRepository
                    .findReportsByUserAndMachine(initialDate, getFinalDate(finalDate), userId, macId));
        } else {
            return reportListGenerate(reportCrudRepository
                    .findReportsByUserAndMachineLimit(initialDate, getFinalDate(finalDate), userId, macId, limit));
        }
    }

    public List<Report> findByMachineStatus(){
        int status = 1; //Will search by machines logged
        return reportListGenerate(reportCrudRepository.findByMachineStatus(status));
    }

    //findReportsByDates

    private List <Report> reportListGenerate(List<Logs> logs){

        Report report;
        List<Report> reportList = new ArrayList();

        for (Logs logItem : logs) {
            report = new Report();
            report.setId(logItem.getId());
            report.setUser(logItem.getUser().getName());
            report.setRec(logItem.getUser().getReg());
            report.setImageName(logItem.getUser().getPicture());
            report.setMacId(logItem.getMachine().getMacid());
            report.setMachine(logItem.getMachine().getDescription());
            report.setStatus(logItem.getStatus());
            report.setLogin(formatTime(logItem.getLogin()));
            report.setLoginDate(formatDaTe(logItem.getLogin()));

            if (logItem.getLogout() != null) {
                report.setLogout(formatTime(logItem.getLogout()));
                report.setLogoutDate(formatDaTe(logItem.getLogout()));
            } else if (logItem.getLogout() == null) {
                report.setLogout("Em operação");
            }
            reportList.add(report);
        }
        return reportList;
    }

    private String formatDaTe(LocalDateTime date){
        //SimpleDateFormat formatter = new  SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return  formatter.format(date);
    }

    private String formatTime(LocalDateTime date){
        //SimpleDateFormat formatter = new  SimpleDateFormat("HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return  formatter.format(date);
    }

    private String getFinalDate(String date) {
        return date + " " + "23:59:59";
    }
}