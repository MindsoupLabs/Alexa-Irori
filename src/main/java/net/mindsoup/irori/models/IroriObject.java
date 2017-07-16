package net.mindsoup.irori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Valentijn on 15-7-2017.
 */
@Entity
@Table(name="irori_objects")
public class IroriObject {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	private String name;

	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
