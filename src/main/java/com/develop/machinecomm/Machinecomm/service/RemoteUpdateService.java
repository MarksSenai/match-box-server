package com.develop.machinecomm.Machinecomm.service;

import com.develop.machinecomm.Machinecomm.client.InterimClient;
import com.develop.machinecomm.Machinecomm.dto.RemoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class RemoteUpdateService {

    private InterimClient client;

    @Autowired
    public RemoteUpdateService(InterimClient client){
        this.client = client;
    }

    public ResponseEntity<?> executeRemoteUpdate(RemoteDTO remote) throws IOException {
        this.client.updateMachine(remote, urlBuilder(remote));
        return null;
    }

    private String urlBuilder(RemoteDTO remote) {
        StringBuilder url = new StringBuilder();

        url.append("http://");
        url.append(remote.getMachineIP());
        url.append(":");
        url.append(remote.getMachinePort());
        url.append("/api/deploy/");

        return url.toString();
    }
}