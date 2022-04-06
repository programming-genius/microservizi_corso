package it.app.registration.dto;

public class RegistrationResponseDTO {

    private BusinessUserDTO user;
    private SimDTO sim;

    private String error;

    public RegistrationResponseDTO(){}

    public RegistrationResponseDTO(BusinessUserDTO userDTO, SimDTO simDTO, String error){
        this.user = userDTO;
        this.sim = simDTO;
        this.error = error;
    }

    public BusinessUserDTO getUser() {
        return user;
    }

    public void setUser(BusinessUserDTO user) {
        this.user = user;
    }

    public SimDTO getSim() {
        return sim;
    }

    public void setSim(SimDTO sim) {
        this.sim= sim;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
