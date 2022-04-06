package it.app.mobile.dto;
import java.util.List;

public class SimDTO {

    private Integer id;
    private String msisdn;
    private String imsi;
    private Integer userId;

    public SimDTO(Integer id, String msisdn, String imsi, Integer userId){
        this.id = id;
        this.msisdn = msisdn;
        this.imsi = imsi;
        this.userId = userId;
    }

    private List<ProductDTO> product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public List<ProductDTO> getProduct() {
        return product;
    }

    public void setProduct(List<ProductDTO> product) {
        this.product = product;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
