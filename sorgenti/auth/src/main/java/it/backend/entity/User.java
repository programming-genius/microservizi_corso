package it.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "public")
@SequenceGenerator(name = "sequence_generator_user", initialValue = 1, allocationSize = 3,
sequenceName = "user_id_seq")
public class User {

	public static  enum EncryptionAlgorithm {
		BCRYPT, SCRYPT, PBKDF2
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator_user")
	private Integer id;
	private String username;
	private String password;
	private int version;

	@Enumerated(EnumType.STRING)
	private EncryptionAlgorithm algorithm;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
	private Set<Role> roles = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EncryptionAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(EncryptionAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}