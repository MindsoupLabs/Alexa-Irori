package net.mindsoup.irori.controllers;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.dtos.response.IroriResponse;
import net.mindsoup.irori.dtos.response.ObjectErrorResponse;
import net.mindsoup.irori.dtos.response.StatErrorResponse;
import net.mindsoup.irori.dtos.response.StatResponse;
import net.mindsoup.irori.exceptions.ObjectNotFoundException;
import net.mindsoup.irori.exceptions.StatNotFoundException;
import net.mindsoup.irori.services.IroriService;
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
}
