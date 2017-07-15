package net.mindsoup.irori.dtos.response;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class StatErrorResponse extends ObjectErrorResponse {

	private final String statName;

	public StatErrorResponse(String objectName, String statName) {
		super(objectName);

		this.statName = statName;
	}

	@Override
	public ResponseType getType() {
		return ResponseType.STAT_ERROR;
	}

	public String getStatName() {
		return statName;
	}
}
