package it.app.aggregator.dto;
import java.io.Serializable;
import java.util.List;

public class BusinessUserDTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private List<SimDTO> sim;
    private String info;

    public  BusinessUserDTO(){}

    public BusinessUserDTO(Integer id, String firstName, String lastName, String fiscalCode){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fiscalCode = fiscalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SimDTO> getSim() {
        return sim;
    }

    public void setSim(List<SimDTO> sim) {
        this.sim = sim;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
