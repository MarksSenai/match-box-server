package com.develop.machinecomm.Machinecomm.service;


import com.develop.machinecomm.Machinecomm.model.Configuration;
import com.develop.machinecomm.Machinecomm.repository.ConfigurationCrudRepository;
import com.develop.machinecomm.Machinecomm.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {

    private ConfigurationRepository configurationRepository;
    private ConfigurationCrudRepository configurationCrudRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository,
                                ConfigurationCrudRepository configurationCrudRepository){
        this.configurationRepository = configurationRepository;
        this.configurationCrudRepository = configurationCrudRepository;
    }

    public Configuration createConfig(Configuration config){
        return this.configurationRepository.save(config);
    }

    public Configuration getConfig(){
        Long configId = 1L; //Id único
        return this.configurationCrudRepository.findConfigById(configId);
    }

    public Configuration updateConfiguration(Configuration config){
        return this.configurationRepository.save(config);
    }

    public Configuration updateCompanyImage(String fileName) {
       Configuration config = getConfig();
       config.setCompanypicture(fileName);

       this.configurationRepository.save(config);
       return config;
    }
}