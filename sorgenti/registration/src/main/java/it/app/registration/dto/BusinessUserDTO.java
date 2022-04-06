package it.app.registration.dto;
import java.io.Serializable;
import java.util.List;

public class BusinessUserDTO implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String fiscalCode;

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

}
