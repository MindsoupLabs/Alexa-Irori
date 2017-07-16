package net.mindsoup.irori.dtos.response;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class InputErrorResponse implements IroriResponse {
	@Override
	public ResponseType getType() {
		return ResponseType.INPUT_ERROR;
	}
}
