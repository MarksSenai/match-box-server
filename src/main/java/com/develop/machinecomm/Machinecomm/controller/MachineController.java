package com.develop.machinecomm.Machinecomm.controller;

import com.develop.machinecomm.Machinecomm.exception.ResourceNotFoundException;
import com.develop.machinecomm.Machinecomm.model.Machine;
import com.develop.machinecomm.Machinecomm.repository.MachineRepository;
import com.develop.machinecomm.Machinecomm.service.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class MachineController {

    private MachineRepository machineRepository;
    private MachineService machineService;

    @Autowired
    public MachineController(MachineRepository machineRepository,
                             MachineService machineService){
        this.machineRepository = machineRepository;
        this.machineService = machineService;
    }

    // Create a new Machine
    @PostMapping("/machine")
    public Machine createMachine(@Valid @RequestBody Machine machine) {

        return this.machineService.createMachine(machine);
    }

    // Get All Machines
    @GetMapping("/machines")
    public List<Machine> getAllMachines() {
        return this.machineService.findMachines();
    }

    // Get a Single Machine
    @GetMapping("/machineid/{id}")
    public Machine getMachineById(@PathVariable(value = "id") Long machineId) {
        return machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", "id", machineId));
    }

    @GetMapping("/searchmachine/{machine}")
    public List<Machine> searchMachine(@PathVariable(value = "machine") String machine){
        return this.machineService.searchMachine(machine);
    }

    // Update a Machine
    @PutMapping("/machine")
    public Machine updateMachine(@Valid @RequestBody Machine machine) {


        return  this.machineService.updateMachine(machine);

    }

}
