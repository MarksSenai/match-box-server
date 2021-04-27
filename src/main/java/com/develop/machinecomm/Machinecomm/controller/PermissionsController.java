package com.develop.machinecomm.Machinecomm.controller;


import com.develop.machinecomm.Machinecomm.dto.PermissionsDTO;
import com.develop.machinecomm.Machinecomm.model.Machine;
import com.develop.machinecomm.Machinecomm.model.Permissions;
import com.develop.machinecomm.Machinecomm.repository.PermissionRepository;
import com.develop.machinecomm.Machinecomm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/portal")
public class PermissionsController {

    private PermissionService permissionService;
    private PermissionRepository permissionRepository;

    @Autowired
    public PermissionsController(PermissionService permissionService,
        PermissionRepository permissionRepository){
        this.permissionService = permissionService;
        this.permissionRepository = permissionRepository;
    }

    // Create a new Permission
    @PostMapping("/permission/{userId}")
    public void createPermission(@PathVariable(value = "userId") Integer userId,
                                        @Valid @RequestBody ArrayList<Machine> machines) throws Exception {
         permissionService.createPermission(userId, machines);

    }

    // Get All Permissions
    @GetMapping("/permissions")
    public List<PermissionsDTO> findAllPermissions() {
        return permissionService.findAllPermissions();
    }

    // Get By User
    @GetMapping("/permissions/user/{userId}")
    public List<PermissionsDTO> findByUser(@PathVariable(value = "userId") Integer userId) {
        return permissionService.findByUser(userId);
    }
    // Get By Machine
    @GetMapping("/permissions/machine/{machineId}")
    public List<PermissionsDTO> findByMachine(@PathVariable(value = "machineId") Long machineId) {
        return permissionService.findByMachine(machineId);
    }

    @DeleteMapping("/permission/{permId}")
    public ResponseEntity<Permissions> deletePermission(@PathVariable(value = "permId") Long permId){
         return permissionService.deletePermissionById(permId);

    }

}
