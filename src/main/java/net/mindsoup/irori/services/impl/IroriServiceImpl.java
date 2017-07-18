package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.exceptions.InvalidInputException;
import net.mindsoup.irori.exceptions.ObjectNotFoundException;
import net.mindsoup.irori.exceptions.StatNotFoundException;
import net.mindsoup.irori.models.IroriObject;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.repositories.ObjectRepository;
import net.mindsoup.irori.repositories.StatRepository;
import net.mindsoup.irori.services.IroriService;
import net.mindsoup.irori.services.TextService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IroriServiceImpl implements IroriService {

	@Autowired
	private TextService textService;

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private StatRepository statRepository;

	@Override
	public IroriStat getStat(StatRequest statRequest) {
		if(StringUtils.isBlank(statRequest.getStatName()) || StringUtils.isBlank(statRequest.getObjectName())) {
			throw new InvalidInputException();
		}

		// attempt to fix alexa's inability to understand some words
		statRequest.setObjectName(textService.getClosestMatch(statRequest.getObjectName()));

		// see if this object is in our db
		IroriObject iroriObject = objectRepository.findByName(statRequest.getObjectName());

		if(iroriObject == null) {
			// not in the db, throw an exception so we can let the user know
			throw new ObjectNotFoundException(statRequest.getObjectName());
		}

		// set any stat synonyms to the correct stat name ('A.C.' to 'armor class', etc)
		statRequest.setStatName(textService.getSynonym(statRequest.getStatName(), iroriObject.getType()));

		// the object is in our db, lets see if we know the stat the user is asking for
		IroriStat iroriStat = statRepository.findByObjectAndStatName(iroriObject, statRequest.getStatName());

		if(iroriStat == null) {
			// we don't have the stat in our db, throw an exception to let the user know
			throw new StatNotFoundException(statRequest.getObjectName(), statRequest.getStatName());
		}

		// return the stat
		return iroriStat;
	}
}
