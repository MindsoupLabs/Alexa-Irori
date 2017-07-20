package net.mindsoup.irori.controllers;

import net.mindsoup.irori.MatchType;
import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.dtos.response.*;
import net.mindsoup.irori.exceptions.InvalidInputException;
import net.mindsoup.irori.exceptions.ObjectNotFoundException;
import net.mindsoup.irori.exceptions.StatNotFoundException;
import net.mindsoup.irori.services.IroriService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping(value = "/irori", method = RequestMethod.POST)
public class IroriController {

	private static final Logger LOG = LoggerFactory.getLogger(IroriController.class);

	@Autowired
	private IroriService iroriService;

	@RequestMapping("/stat")
	public IroriResponse getStat(@RequestBody StatRequest statRequest) {
		LOG.debug(statRequest.toString());

		if(StringUtils.isBlank(statRequest.getObjectType()) || MatchType.valueOf(statRequest.getObjectType().toUpperCase()) == null) {
			statRequest.setObjectType(MatchType.OBJECT.toString());
		}
		statRequest.setObjectName(statRequest.getObjectName().toLowerCase());
		statRequest.setStatName(statRequest.getStatName().toLowerCase());
		return new StatResponse(iroriService.getStat(statRequest));
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public IroriResponse objectError(ObjectNotFoundException e) {
		LOG.warn(e.getMessage());

		return new ObjectErrorResponse(e.getObjectName());
	}

	@ExceptionHandler(StatNotFoundException.class)
	public IroriResponse statError(StatNotFoundException e) {
		LOG.warn(e.getMessage());

		return new StatErrorResponse(e.getObjectName(), e.getStatName());
	}

	@ExceptionHandler(InvalidInputException.class)
	public IroriResponse inputError(InvalidInputException e) {
		LOG.warn(e.getMessage());

		return new InputErrorResponse();
	}
}
