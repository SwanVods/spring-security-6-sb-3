package com.feira.joblist.controller;

import com.feira.joblist.model.Job;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping("/jobs-list")
    public ResponseEntity<List<Job>> getData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
        ResponseEntity<Job[]> response = restTemplate.getForEntity(url, Job[].class);
        List<Job> dataList = Arrays.asList(response.getBody());
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/job-list/{id}")
    public ResponseEntity<Job> getDataById(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions/{id}";
        return restTemplate.exchange(url, HttpMethod.GET, null, Job.class, id);
    }


}
