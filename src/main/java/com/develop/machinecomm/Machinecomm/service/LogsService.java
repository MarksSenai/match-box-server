package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.dto.LogDTO;
import com.develop.machinecomm.Machinecomm.exception.FileStorageException;
import com.develop.machinecomm.Machinecomm.exception.ResourceNotFoundException;
import com.develop.machinecomm.Machinecomm.model.Logs;
import com.develop.machinecomm.Machinecomm.model.Machine;
import com.develop.machinecomm.Machinecomm.model.Users;
import com.develop.machinecomm.Machinecomm.model.enums.Perfil;
import com.develop.machinecomm.Machinecomm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LogsService extends ResourceNotFoundException {

    private LogsCrudRepository logsCrudRepository;
    private LogsRepository logsRepository;
    private UserCrudRepository userCrudRepository;
    private UserRepository userRepository;
    private MachineCrudRepository machineCrudRepository;
    private MachineRepository machineRepository;
    private PermissionService permissionService;
    private UserService userService;
    private MachineService machineService;
    private ConfigurationService configurationService;
    private final static Logger logger = Logger.getLogger(UserService.class.getName());
    private LogDTO logRespose;
    private Perfil perfis;

    @Autowired
    public LogsService(LogsCrudRepository logsCrudRepository,
                       LogsRepository logsRepository,
                       UserCrudRepository userCrudRepository,
                       UserRepository userRepository,
                       MachineCrudRepository machineCrudRepository,
                       PermissionService permissionService,
                       UserService userService,
                       MachineService machineService,
                       ConfigurationService configurationService
    ) {
        this.logsRepository = logsRepository;
        this.userCrudRepository = userCrudRepository;
        this.userRepository = userRepository;
        this.machineCrudRepository = machineCrudRepository;
        this.logsCrudRepository = logsCrudRepository;
        this.permissionService = permissionService;
        this.userService = userService;
        this.machineService = machineService;
        this.configurationService = configurationService;
    }

    public LogDTO manageLog(String rfId, String macId) {
        int logStatus = 1;
        int statusMaster = 3;// O LOG_STATUS Master pertence ao administrador de área que possui

        Users user = this.userCrudRepository.findByUserRfid(rfId);
        if (user == null) {
            logRespose = new LogDTO();
            logRespose.setMessage("Usuário não cadastrado");
            logRespose.setUser("");
            logRespose.setUserStatus("Desconhecido");
            logRespose.setUserSubscribed(false);
            logRespose.setLogstatus(false);

            return logRespose;
        }
        Machine machine = this.machineCrudRepository.findByMacidIgnoreCase(macId);
        Logs logOfUser = this.logsCrudRepository.findByUserLogged(user.getId(), logStatus); //Onde o Usuário está logado
        Logs logOfMachine = this.logsCrudRepository.checkLogMachine(machine.getId(), logStatus); //Quem está logado na máquina

        int access = checkExpirationLack(user.getId(), machine.getId());

        boolean permission = permissionService.findPermissionByUserAndMachine(
                user.getId(), machine.getId());

        if (logOfUser == null && logOfMachine == null && (access == 1 || access == 3) &&
                (permission || user.getType() == 3)) {
            try {
                if (access == 1){
                    updateLogoutRecord(user, machine);
                }
                Logs log = createLoginRecord(user, machine, logStatus);

                if (log != null) {
                    logRespose = new LogDTO();
                    logRespose.setMessage("Logado");
                    logRespose.setUser(user.getName());
                    logRespose.setUserId(Long.valueOf(user.getId().toString()));
                    logRespose.setUserRec(user.getReg());
                    logRespose.setUserStatus(userStatus(user.getType()));
                    logRespose.setUserPicture(user.getPicture());
                    logRespose.setLogstatus(true);
                    logRespose.setUserSubscribed(true);
                    return logRespose;
                }
            } catch (FileStorageException e) {
                throw new FileStorageException("Erro ao criar log", e);
            }
        } else if (logOfUser != null && logOfMachine == null && (access == 1 || access == 3) &&
                (permission || user.getType() == statusMaster)) {
            try{
                Machine machineToLogoutUser = this.machineCrudRepository
                        .findMachineById(logOfUser.getMachine().getId());

                createLogoutRecord(user, machineToLogoutUser, logStatus);
                if (access == 1){
                    updateLogoutRecord(user, machine);
                }

                Logs log = createLoginRecord(user, machine, logStatus);
                if (log != null) {
                    logRespose = new LogDTO();
                    logRespose.setMessage("Operador logado em outro equipamento!");
                    logRespose.setUser(user.getName());
                    logRespose.setUserId(Long.valueOf(user.getId().toString()));
                    logRespose.setUserRec(user.getReg());
                    logRespose.setUserStatus(userStatus(user.getType()));
                    logRespose.setLogstatus(false);
                    logRespose.setUserSubscribed(true);
                    return logRespose;
                }
            }catch(FileStorageException e){
                throw new FileStorageException("Erro ao criar log", e);
            }

        } else if (logOfUser == null && logOfMachine != null && (access == 1 || access == 3) &&
                (permission || user.getType() == statusMaster)) {
            try {
                Users userToLoggoutMachine = this.userCrudRepository
                        .findByUserId(String.valueOf(logOfMachine.getUser().getId()));
                createLogoutRecord(userToLoggoutMachine, machine, logStatus);
                if (access == 1){
                    updateLogoutRecord(user, machine);
                }

                Logs log = createLoginRecord(user, machine, logStatus);
                if (log != null) {
                    logRespose = new LogDTO();
                    logRespose.setMessage("Logado");
                    logRespose.setUser(user.getName());
                    logRespose.setUserId(Long.valueOf(user.getId().toString()));
                    logRespose.setUserRec(user.getReg());
                    logRespose.setUserStatus(userStatus(user.getType()));
                    logRespose.setUserPicture(user.getPicture());
                    logRespose.setLogstatus(true);
                    logRespose.setUserSubscribed(true);
                    return logRespose;
                }
            }catch(FileStorageException e){
                throw new FileStorageException("Erro ao criar log", e);
            }
        } else if (logOfUser != null && logOfMachine != null && (access == 1 || access == 3) &&
                (permission || user.getType() == statusMaster)
                && (logOfUser.getMachine().getId() != machine.getId())) {
            try {
                    logRespose = new LogDTO();
                    logRespose.setMessage("Usuário " + logOfUser.getUser().getName() + " já logado em " +
                            logOfUser.getMachine().getMacid());
                    logRespose.setUser(user.getName());
                    logRespose.setUserRec(null);
                    logRespose.setUserStatus(null);
                    logRespose.setUserId(null);
                    logRespose.setLogstatus(false);
                    return logRespose;
            } catch(FileStorageException e) {
                throw new FileStorageException("Erro ao criar log", e);
            }
        } else if (permission == false) {
            logRespose = new LogDTO();
            logRespose.setMessage("Sem permissão");
            logRespose.setUser(user.getName());
            logRespose.setUserRec(user.getReg());
            logRespose.setUserStatus(userStatus(user.getType()));
            logRespose.setLogstatus(false);
            logRespose.setUserSubscribed(true);
            return logRespose;

        } else if (logOfUser != null && logOfMachine != null
                && (logOfUser.getMachine().getId() == machine.getId())) {
            createLogoutRecord(user, machine, logStatus);
            logRespose = new LogDTO();
            logRespose.setMessage("Deslogado");
            logRespose.setUser(user.getName());
            logRespose.setUserId(Long.valueOf(user.getId()));
            logRespose.setUserRec(user.getReg());
            logRespose.setUserStatus(userStatus(user.getType()));
            logRespose.setUserPicture(user.getPicture());
            logRespose.setUserSubscribed(true);
            logRespose.setLogstatus(false);
            return logRespose;
        } else if ((logOfUser == null && logOfMachine == null && access == 2)
                || (logOfUser != null && logOfMachine == null && access == 2)
                || (logOfUser == null && logOfMachine != null && access == 2)
                || (logOfUser != null && logOfMachine != null && access == 2)) {
            logStatus = 2;
            Logs log = this.logsCrudRepository
                    .findByUserAndMachineAndLogout(user.getId(), machine.getId(), logStatus);
            logger.setLevel(Level.WARNING);

            logRespose = new LogDTO();
            logRespose.setMessage("Em carência");
            logRespose.setUser(user.getName());
            logRespose.setUserId(Long.valueOf(user.getId()));
            logRespose.setUserRec(user.getReg());
            logRespose.setUserStatus(userStatus(user.getType()));
            logRespose.setUserSubscribed(true);
            logRespose.setLogstatus(false);
            return logRespose;
        }
        return null;
    }

    private Logs createLoginRecord(Users user, Machine machine, int status) {
        int statusLogin = 1; //status 1 corresponde ao login efetuado e logout pendente
        Logs log = new Logs();
        LocalDateTime dateTime =  getCurrentDateTime();

        log.setUser(user);
        log.setMachine(machine);
        log.setLogin(dateTime);
        log.setStatus(statusLogin);
        return logsRepository.save(log);
    }

    private void createLogoutRecord(Users user, Machine machine, int status) {
        int statusLogout = 2; //O statusLogout 2 corresponde ao logout já efetuado.

        Logs log = logsCrudRepository.findByUserAndMachineAndLogout(user.getId(), machine.getId(), status);
        LocalDateTime dateTime = getCurrentDateTime();
        log.setLogout(dateTime);

        log.setExpirationlack(getExpirationTime());
        log.setStatus(statusLogout);

        logsRepository.save(log);
    }

    private LocalDateTime getCurrentDateTime(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        System.out.println("Current time *****: "+now);
        return now;
    }

    private LocalDateTime getExpirationTime(){
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime nextTime = currentTime.plusMinutes
                (Long.parseLong(this.configurationService.getConfig().getExpirationtime()));
        return nextTime;

    }

    private int checkExpirationLack(Integer userId, Long machineId){
        int expiration = 1;
        int expirationStatus = 2;

        Logs log = this.logsCrudRepository
                .findByUserAndMachineAndLogout(userId, machineId, expirationStatus);
        if (log == null){
            expiration = 3;//status 3: Não foi encontrado log com carência.
        } else {
            LocalDateTime expirationTime = log.getExpirationlack();

            LocalDateTime currentTime = getCurrentDateTime();

            if(expirationTime.compareTo(currentTime) < 0){
                expiration = 1; // status 1: Encontrato sem carência, já expirado.
            } else if (expirationTime.compareTo(currentTime) > 0) {
                expiration = 2; // status 2: Encontrato com carência, por expirar.
            }
        }
        return expiration;
    }

    private void updateLogoutRecord(Users user, Machine machine) {
        int status = 2; //O statusLogout 2 corresponde ao logout já efetuado.
        int statusAfterExpiration = 3;
        Logs log = logsCrudRepository.findByUserAndMachineAndLogout(user.getId(), machine.getId(), status);

        log.setStatus(statusAfterExpiration);
        logsRepository.save(log);
    }

    private String userStatus(int userStatus) {
        String userStatusDescription = "";
        switch (userStatus) {
            case 1:
                userStatusDescription = "Inativo";
                break;
            case 2:
                userStatusDescription = "Habilitado";
                break;
            case 3:
                userStatusDescription = "Especialista";
                break;
            case 4:
                userStatusDescription = "Treinamento";
                break;
        }
        return userStatusDescription;
    }
}