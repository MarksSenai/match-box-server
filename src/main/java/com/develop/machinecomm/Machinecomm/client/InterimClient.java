package com.develop.machinecomm.Machinecomm.client;

import com.develop.machinecomm.Machinecomm.dto.RemoteDTO;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

@Service
public class InterimClient {

    public void updateMachine(RemoteDTO remote, String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        String remoteJSON = mapper.writeValueAsString(remote);
        HttpEntity<String> request = new HttpEntity<>(remoteJSON);
        restTemplate.postForObject(url, request, String.class);

    }
}
