package net.mindsoup.irori.dtos.request;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class StatRequest {
	private String statName;
	private String objectName;

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
