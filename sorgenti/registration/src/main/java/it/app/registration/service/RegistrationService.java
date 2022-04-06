package it.app.registration.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import it.app.registration.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${anagrafica.url}")
    private String USER_URL;
    @Value("${sim.url}")
    private String SIM_URL;

    @HystrixCommand(fallbackMethod = "createUserError", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<BusinessUserDTO> createUser(RegistrationDTO user, HttpHeaders headers) {
        //Crea utente
        HttpEntity<BusinessUserDTO> request = new HttpEntity<>(user.getUser(), headers);
        ResponseEntity<BusinessUserDTO> businessUserDTO = restTemplate.
                postForEntity(USER_URL+"/user/create",request,
                        BusinessUserDTO.class);
        return businessUserDTO;
    }

    @HystrixCommand(fallbackMethod = "getSimError",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<SimDTO> getSim(HttpHeaders headers){
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                SIM_URL+"/sim/free",
                HttpMethod.GET, requestEntity, SimDTO.class);
    }

    @HystrixCommand(fallbackMethod = "acquireSimError",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<SimDTO> acquireSim(ResponseEntity<SimDTO> simDTO, HttpHeaders headers){
        HttpEntity<SimDTO> entitySim = new HttpEntity<SimDTO>(simDTO.getBody(),headers);
        return restTemplate.exchange(SIM_URL+"/sim/user/activate",
                HttpMethod.PUT, entitySim, SimDTO.class);
    }

    @HystrixCommand(fallbackMethod = "activateProductError",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    public ResponseEntity<SimDTO> activateProduct(RegistrationDTO user, ResponseEntity<SimDTO> simDTO, HttpHeaders headers){
        HttpEntity<List<ProductDTO>> entityProduct =
                new HttpEntity<List<ProductDTO>>(user.getProduct(),headers);
        return restTemplate.exchange(SIM_URL+"/sim/activate/list/"+simDTO.getBody().getId(),
                HttpMethod.PUT, entityProduct, SimDTO.class);
    }

    public ResponseEntity<BusinessUserDTO> createUserError(RegistrationDTO user, HttpHeaders headers){
        return new ResponseEntity<BusinessUserDTO>(user.getUser(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<SimDTO> getSimError(HttpHeaders headers){
        return new ResponseEntity<SimDTO>(new SimDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<SimDTO> acquireSimError(ResponseEntity<SimDTO> simDTO, HttpHeaders headers){
        return new ResponseEntity<SimDTO>(simDTO.getBody(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<SimDTO> activateProductError(RegistrationDTO user, ResponseEntity<SimDTO> simDTO, HttpHeaders headers){
        return new ResponseEntity<SimDTO>(simDTO.getBody(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
