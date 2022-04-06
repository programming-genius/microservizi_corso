package it.app.mobile.entity;

import javax.persistence.*;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "sequence_generator_product",initialValue = 1,allocationSize = 3,
        sequenceName = "product_id_seq")
public class Product {

    private Integer id;
    private String name;
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "sequence_generator_product")
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object product){
        if(product==null){
            return false;
        }
        return id != null && id.equals(((Product)product).id);
    }

    @Override
    public int hashCode(){
        return 1;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
