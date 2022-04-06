package it.app.mobile.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sim")
@SequenceGenerator(name = "sequence_generator_sim",initialValue = 1, allocationSize = 3,
        sequenceName = "sim_id_seq")
public class Sim {

    private Integer id;
    private String msisdn;
    private String imsi;
    private Integer userId;
    private Set<Product> products;
    private int version;

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "sequence_generator_sim")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "msisdn")
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Column(name = "imsi")
    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "sim_product",
            joinColumns = {@JoinColumn(name = "sim_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    public Set<Product> getProducts() {
        if(products==null)
            products = new HashSet<>();
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Column(name = "userid")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object sim){
        if(sim==null){
            return false;
        }
        return id != null && id.equals(((Sim)sim).id);
    }

    @Override
    public int hashCode(){
        return 1;
    }

    @Override
    public String toString() {
        return "Sim{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", imsi='" + imsi + '\'' +
                '}';
    }
}
