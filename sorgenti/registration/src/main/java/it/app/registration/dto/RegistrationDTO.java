package it.app.registration.dto;

import java.util.List;

public class RegistrationDTO {

    private BusinessUserDTO user;
    private List<ProductDTO> product;

    public BusinessUserDTO getUser() {
        return user;
    }

    public void setUser(BusinessUserDTO user) {
        this.user = user;
    }

    public List<ProductDTO> getProduct() {
        return product;
    }

    public void setProduct(List<ProductDTO> product) {
        this.product = product;
    }
}
