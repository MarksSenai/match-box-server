package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.model.Machine;
import com.develop.machinecomm.Machinecomm.repository.MachineCrudRepository;
import com.develop.machinecomm.Machinecomm.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
    private MachineCrudRepository machineCrudRepository;
    private MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineCrudRepository machineCrudRepository,
                          MachineRepository machineRepository) {
        this.machineCrudRepository = machineCrudRepository;
        this.machineRepository = machineRepository;
    }

    public Machine createMachine (Machine machine) {
        return this.machineRepository.save(machine);
    }

    public List<Machine> findMachines() {
        return machineCrudRepository.findAllMachines();
    }

    public Machine findByMacId(String macid) {
        return machineCrudRepository.findByMacidIgnoreCase(macid);
    }

    public Machine updateMachine (Machine machine) {
        return  this.machineRepository.save(machine);
    }

    public List<Machine> searchMachine(String machine){
        return this.machineCrudRepository.searchMachine(machine);
    }
}
