package net.mindsoup.irori.exceptions;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class InvalidInputException extends IroriException {

	@Override
	public String getMessage() {
		return String.format("Invalid input");
	}
}
