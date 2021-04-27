package com.develop.machinecomm.Machinecomm.controller;

import com.develop.machinecomm.Machinecomm.dto.LogDTO;
import com.develop.machinecomm.Machinecomm.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class LogsController {

    @Autowired
    LogsService logsService;

    @RequestMapping("/logon/{rfid}/{macId}")
    public LogDTO manageLogon(@PathVariable(value = "rfid") String rfid,
                              @PathVariable(value = "macId") String macId) {
       return logsService.manageLog(rfid, macId);
    }
}