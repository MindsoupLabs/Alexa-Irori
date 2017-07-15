package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.exceptions.ObjectNotFoundException;
import net.mindsoup.irori.exceptions.StatNotFoundException;
import net.mindsoup.irori.models.IroriObject;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.repositories.ObjectRepository;
import net.mindsoup.irori.repositories.StatRepository;
import net.mindsoup.irori.services.IroriService;
import net.mindsoup.irori.services.SynonymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class IroriServiceImpl implements IroriService {

	@Autowired
	private SynonymService synonymService;

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private StatRepository statRepository;

	@Override
	public IroriStat getStat(StatRequest statRequest) {
		// set any stat synonyms to the correct stat name ('A.C.' to 'armor class', etc)
		statRequest.setStatName(synonymService.getSynonym(statRequest.getStatName()));

		IroriObject iroriObject = objectRepository.findByName(statRequest.getObjectName());

		if(iroriObject == null) {
			throw new ObjectNotFoundException(statRequest.getObjectName());
		}

		IroriStat iroriStat = statRepository.findByObjectAndStat(iroriObject, statRequest.getStatName());

		if(iroriStat == null) {
			throw new StatNotFoundException(statRequest.getObjectName(), statRequest.getStatName());
		}

		return iroriStat;
	}
}
