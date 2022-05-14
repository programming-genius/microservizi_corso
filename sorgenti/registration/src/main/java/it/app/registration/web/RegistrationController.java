package it.app.registration.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import it.app.registration.dto.*;
import it.app.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class RegistrationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RegistrationService registrationService;

    @Value("${anagrafica.url}")
    private String USER_URL;
    @Value("${sim.url}")
    private String SIM_URL;

    /*@HystrixCommand(fallbackMethod = "serviceUnavailable", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "12000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "30"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "25"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "20000")
    })*/
    @PostMapping(value="/registration", consumes="application/json", produces="application/json")
    public ResponseEntity<RegistrationResponseDTO> create(@RequestBody RegistrationDTO user,
                                                          @RequestHeader("Authorization")
                                                          String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",authorization);

        // Crea utente
        ResponseEntity<BusinessUserDTO> businessUserDTO = registrationService.createUser(user, headers);
        ResponseEntity<RegistrationResponseDTO> response = checkUser(businessUserDTO);
        if(response != null) return response;

        //Recupera sim
        ResponseEntity<SimDTO> simDTO = registrationService.getSim(headers);
        response = checkSim(simDTO,businessUserDTO,"Registrazione avvenuta con successo ma non ci sono " +
                "Sim disponibili, contatta l'assistenza clienti al numero ...");
        if(response != null) return response;

        //Acquisisci sim
        simDTO = registrationService.acquireSim(simDTO,headers);
        response = checkSim(simDTO,businessUserDTO,"Errore nella registrazione della Sim, " +
                "contatta l'assistenza clienti al numero ...");
        if(response != null) return response;

        //Attivare i prodotti
        simDTO = registrationService.activateProduct(user, simDTO, headers);
        response = checkSim(simDTO,businessUserDTO,"Errore nell'attivazione dei prodotti sulla Sim, " +
                "contatta l'assistenza clienti al numero ...");
        if(response != null) return response;

        return new ResponseEntity<RegistrationResponseDTO>(
                new RegistrationResponseDTO(businessUserDTO.getBody(),simDTO.getBody(),null),
                businessUserDTO.getStatusCode());
    }

    /*public ResponseEntity<RegistrationResponseDTO> serviceUnavailable(RegistrationDTO user,
                                                                      String authorization){
        RegistrationResponseDTO businessUserDTO = new RegistrationResponseDTO();
        businessUserDTO.setError("Servizio non raggiungibile");
        businessUserDTO.setUser(user.getUser());
        return new ResponseEntity<RegistrationResponseDTO>(businessUserDTO, HttpStatus.SERVICE_UNAVAILABLE);
    }*/

    private ResponseEntity<RegistrationResponseDTO> checkUser(ResponseEntity<BusinessUserDTO> businessUserDTO){
        if(businessUserDTO.getStatusCode()!=HttpStatus.CREATED){
            return new ResponseEntity<RegistrationResponseDTO>(
                    new RegistrationResponseDTO(null,null,
                            "Errore nella registrazione dell'utente"),
                    businessUserDTO.getStatusCode());
        }
        return null;
    }

    public ResponseEntity<RegistrationResponseDTO> checkSim(ResponseEntity<SimDTO> simDTO, ResponseEntity<BusinessUserDTO> businessUserDTO, String errorMessage){
        if(simDTO.getStatusCode()==HttpStatus.OK)
            simDTO.getBody().setUserId(businessUserDTO.getBody().getId());
        else
            return new ResponseEntity<RegistrationResponseDTO>(
                    new RegistrationResponseDTO(businessUserDTO.getBody(),simDTO!=null?simDTO.getBody():null, errorMessage),
                    businessUserDTO.getStatusCode());
        return null;
    }
}
