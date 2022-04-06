package it.app.aggregator.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import it.app.aggregator.dto.BusinessUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OperatorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${anagrafica.url}")
    private String USER_URL;
    @Value("${sim.url}")
    private String SIM_URL;

    @HystrixCommand(fallbackMethod = "getUserError", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<BusinessUserDTO> getUser(Integer id, HttpHeaders headers) {
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                USER_URL+"/user/findById/"+id,
                HttpMethod.GET, requestEntity, BusinessUserDTO.class);
    }

    public ResponseEntity<BusinessUserDTO> getUserError(Integer id, HttpHeaders headers){
        return new ResponseEntity<>(new BusinessUserDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @HystrixCommand(fallbackMethod = "getSimError", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<List> getSim(Integer id, HttpHeaders headers){
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                SIM_URL+"/sim/user/"+id,
                HttpMethod.GET, requestEntity, List.class);
    }

    public ResponseEntity<List> getSimError(Integer id, HttpHeaders headers){
        return new ResponseEntity<List>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
