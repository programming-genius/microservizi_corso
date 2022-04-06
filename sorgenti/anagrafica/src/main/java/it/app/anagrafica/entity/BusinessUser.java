package it.app.anagrafica.entity;
import javax.persistence.*;

@Entity
@Table(name = "business_user")
@SequenceGenerator(name = "sequence_generator", initialValue = 1, allocationSize = 3,
        sequenceName = "business_user_id_seq")
public class BusinessUser {

    private Integer id;
    private String firstName;
    private String lastName;
    private String fiscalCode;
    private int version;

    @Version
    @Column(name="version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version){
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "fiscalcode")
    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Override
    public boolean equals(Object businessUser) {
        if (businessUser == null) {
            return false;
        }
        return id != null && id.equals(((BusinessUser) businessUser).id);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                '}';
    }
}
