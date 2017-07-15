package net.mindsoup.irori.exceptions;

/**
 * Created by Valentijn on 15-7-2017.
 */
public class ObjectNotFoundException extends IroriException {

	private final String objectName;

	public ObjectNotFoundException(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	@Override
	public String getMessage() {
		return String.format("Object %s not found.", getObjectName());
	}
}
