package net.mindsoup.irori.models;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class IroriStats {
	private String objectName;
	private String statName;
	private String statValue;

	public IroriStats(String objectName, String statName, String statValue) {
		this.objectName = objectName;
		this.statName = statName;
		this.statValue = statValue;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
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
}
