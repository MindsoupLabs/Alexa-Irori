package net.mindsoup.irori.exceptions;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class StatNotFoundException extends IroriException {

	private final String objectName;
	private final String statName;

	public StatNotFoundException(String objectName, String statName) {
		this.objectName = objectName;
		this.statName = statName;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getStatName() {
		return statName;
	}

	@Override
	public String getMessage() {
		return String.format("Stat %s not found for object %s.", getStatName(), getObjectName());
	}
}
