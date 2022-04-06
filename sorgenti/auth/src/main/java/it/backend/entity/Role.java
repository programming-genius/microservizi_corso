package it.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "public")
@SequenceGenerator(name = "sequence_generator_role", initialValue = 1, allocationSize = 3,
		sequenceName = "role_id_seq")
public class Role {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator_role")
	private Integer id;
	private String name;
	private int version;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
