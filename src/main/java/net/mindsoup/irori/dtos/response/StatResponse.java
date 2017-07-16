package net.mindsoup.irori.dtos.response;

import net.mindsoup.irori.models.IroriStat;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class StatResponse implements IroriResponse {

	private final String objectName;
	private final String statName;
	private final String value;

	public StatResponse(String objectName, String statName, String value) {
		this.objectName = objectName;
		this.statName = statName;
		this.value = value;
	}

	public StatResponse(IroriStat stats) {
		this(stats.getObjectName(), stats.getStatName(), stats.getStatValue());
	}


	@Override
	public ResponseType getType() {
		return ResponseType.STAT;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getStatName() {
		return statName;
	}

	public String getValue() {
		return value;
	}
}
