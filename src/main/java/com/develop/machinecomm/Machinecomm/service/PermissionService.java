package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.dto.PermissionsDTO;
import com.develop.machinecomm.Machinecomm.error.CustomErrorType;
import com.develop.machinecomm.Machinecomm.exception.PermissionException;
import com.develop.machinecomm.Machinecomm.model.Machine;
import com.develop.machinecomm.Machinecomm.model.Permissions;
import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.repository.PermissionCrudRepository;
import com.develop.machinecomm.Machinecomm.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PermissionService extends PermissionException {

    private PermissionRepository permissionRepository;
    private PermissionCrudRepository permissionCrudRepository;
    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public PermissionService(PermissionRepository permissionRepository,
                             PermissionCrudRepository permissionCrudRepository){
        this.permissionRepository = permissionRepository;
        this.permissionCrudRepository = permissionCrudRepository;
    }

    public void createPermission(Integer userId, ArrayList<Machine> machines) {

        try{
            Permissions permission;
            Users user;
            for (Machine machine : machines) {
                if(permissionCrudRepository.findByUserAndMachine
                        (userId, machine.getId()) == null){
                    permission = new Permissions();
                    user = new Users();
                    user.setId(userId);
                    permission.setUser(user);
                    permission.setMachine(machine);
                    permissionRepository.save(permission);

                } else {
                    logger.log( Level.SEVERE, "Subscribed for "+machine.getDescription());
                }
            }
        }catch (Exception e){
            logger.log( Level.SEVERE, "Could not create permission");
        }
    }

    public ResponseEntity<Permissions> deletePermissionById(Long id){
        Permissions permission = permissionCrudRepository.findByPermissionId(id);

        if (permission == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        permissionRepository.delete(permission);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public List<PermissionsDTO> findAllPermissions(){
        return PermissionsListGenerate(permissionCrudRepository.findAllPermissions());
    }

    public List <PermissionsDTO> findByUser(Integer userId){
        return PermissionsListGenerate(permissionCrudRepository.findByUser(userId));
    }

    public List <PermissionsDTO> findByMachine(Long machineId){
        return PermissionsListGenerate(permissionCrudRepository.findByMachine(machineId));
    }

    public boolean findPermissionByUserAndMachine(Integer userId, Long machineId){
        if (permissionCrudRepository.findByUserAndMachine(userId, machineId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public List <PermissionsDTO> PermissionsListGenerate(List<Permissions> permission){
        PermissionsDTO perm = null;
        List<PermissionsDTO> permissionsDTO = new ArrayList();

        for (Permissions permItem : permission) {
            perm = new PermissionsDTO();
            perm.setId(permItem.getId());
            perm.setUserId(permItem.getUser().getId());
            perm.setUserName(permItem.getUser().getName());
            perm.setMachineId(permItem.getMachine().getId());
            perm.setMachineName(permItem.getMachine().getMacid());

            permissionsDTO.add(perm);
        }
        return permissionsDTO;
    }
}