package it.app.aggregator.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import it.app.aggregator.dto.*;
import it.app.aggregator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AggregatorController {

    @Autowired
    private OperatorService operatorService;

    @HystrixCommand(fallbackMethod = "serviceUnavailable", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })
    @GetMapping(value="/operator/{id}", produces="application/json")
    public ResponseEntity<BusinessUserDTO> getData(@PathVariable Integer id,
                                                   @RequestHeader("Authorization") String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",authorization);

        // Recupera utente
        ResponseEntity<BusinessUserDTO> businessUserDTO = operatorService.getUser(id, headers);
        BusinessUserDTO businessUser=null;

        if(businessUserDTO.getStatusCode()==HttpStatus.OK){
            //Recupera sim
            ResponseEntity<List> simDTO = operatorService.getSim(id,headers);
            businessUser = businessUserDTO.getBody();
            if(simDTO.getStatusCode()==HttpStatus.OK){
                businessUser.setSim((List<SimDTO>) simDTO.getBody());
            } else {
                businessUser.setInfo("Sim non disponibili");
            }
        } else {
            businessUser = new BusinessUserDTO();
            businessUser.setInfo("Non è stato possibile recuperare l'utente");
        }

        return new ResponseEntity<BusinessUserDTO>(businessUser, businessUserDTO.getStatusCode());
    }

    public ResponseEntity<BusinessUserDTO> serviceUnavailable(Integer id, String authorization){
        return new ResponseEntity<>(new BusinessUserDTO(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
