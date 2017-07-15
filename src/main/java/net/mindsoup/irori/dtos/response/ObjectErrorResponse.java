package net.mindsoup.irori.dtos.response;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class ObjectErrorResponse implements IroriResponse {

	private final String objectName;

	public ObjectErrorResponse(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public ResponseType getType() {
		return ResponseType.OBJECT_ERROR;
	}

	public String getObjectName() {
		return objectName;
	}
}
