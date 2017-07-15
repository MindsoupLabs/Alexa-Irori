package net.mindsoup.irori.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Valentijn on 15-7-2017.
 */
@Entity
@Table(name="irori_stats")
public class IroriStat {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="object")
	private IroriObject object;

	@Column(name="stat")
	private String statName;

	@Column(name="value")
	private String statValue;

	public IroriObject getObject() {
		return object;
	}

	public String getObjectName() {
		return object.getName();
	}

	public void setObject(IroriObject object) {
		this.object = object;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getStatValue() {
		return statValue;
	}

	public void setStatValue(String statValue) {
		this.statValue = statValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
