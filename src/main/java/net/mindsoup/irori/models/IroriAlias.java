package net.mindsoup.irori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Valentijn on 23-7-2017
 */
@Entity
@Table(name="irori_aliases")
public class IroriAlias {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@ManyToOne
	@JoinColumn(name="object")
	private IroriObject object;

	@Column(name="alias")
	private String alias;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IroriObject getObject() {
		return object;
	}

	public void setObject(IroriObject object) {
		this.object = object;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
