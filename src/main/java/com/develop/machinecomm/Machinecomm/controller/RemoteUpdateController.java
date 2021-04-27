package com.develop.machinecomm.Machinecomm.controller;

import com.develop.machinecomm.Machinecomm.dto.RemoteDTO;
import com.develop.machinecomm.Machinecomm.service.RemoteUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@CrossOrigin
@RestController
@RequestMapping("/portal")
public class RemoteUpdateController {

    private RemoteUpdateService remoteService;

    @Autowired
    public RemoteUpdateController(RemoteUpdateService remoteService){
        this.remoteService = remoteService;
    }

    @PostMapping("/remoteUpdate")
    public ResponseEntity<?> remoteUpdate(@Valid @RequestBody RemoteDTO remote) throws IOException {
        return remoteService.executeRemoteUpdate(remote);
    }
}