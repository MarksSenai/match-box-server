package com.develop.machinecomm.Machinecomm.controller;


import com.develop.machinecomm.Machinecomm.dto.RemoteDTO;
import com.develop.machinecomm.Machinecomm.model.Configuration;
import com.develop.machinecomm.Machinecomm.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class ConfigurationController {

    private ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService){
        this.configurationService = configurationService;
    }

    @PostMapping("/configuration")
    public Configuration createConfiguration(@Valid @RequestBody Configuration config){
        return this.configurationService.createConfig(config);
    }

    @PostMapping("/remoteConfiguration")
    public Configuration remoteConfiguration(@Valid @RequestBody RemoteDTO remote){
//        return this.configurationService.createConfig(remote);
        return null;
    }

    @GetMapping("/configuration")
    public Configuration getConfiguration(){
        return this.configurationService.getConfig();
    }

    @PutMapping("/configuration")
    public Configuration updateConfiguration(@Valid @RequestBody Configuration config){
        return this.configurationService.updateConfiguration(config);
    }
}

