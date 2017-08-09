package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.dtos.request.StatRequest;
import net.mindsoup.irori.enums.MatchType;
import net.mindsoup.irori.exceptions.InvalidInputException;
import net.mindsoup.irori.exceptions.ObjectNotFoundException;
import net.mindsoup.irori.exceptions.StatNotFoundException;
import net.mindsoup.irori.models.IroriAlias;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.repositories.AliasRepository;
import net.mindsoup.irori.repositories.StatRepository;
import net.mindsoup.irori.services.IroriService;
import net.mindsoup.irori.services.TextService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class IroriServiceImpl implements IroriService {

	private static final Logger LOG = LoggerFactory.getLogger(IroriServiceImpl.class);

	@Autowired
	private TextService textService;

	@Autowired
	private AliasRepository aliasRepository;

	@Autowired
	private StatRepository statRepository;

	private Map<String, MatchType> statMatchTypeMap;

	public IroriServiceImpl() {
		statMatchTypeMap = new HashMap<>();
		statMatchTypeMap.put("hit points", MatchType.MONSTER);
		statMatchTypeMap.put("price", MatchType.ITEM);
		statMatchTypeMap.put("caster level", MatchType.ITEM);
		statMatchTypeMap.put("slot", MatchType.ITEM);
		statMatchTypeMap.put("requirements", MatchType.ITEM);
		statMatchTypeMap.put("cost", MatchType.ITEM);
		statMatchTypeMap.put("school", MatchType.SPELL);
		statMatchTypeMap.put("duration", MatchType.SPELL);
		statMatchTypeMap.put("casting time", MatchType.SPELL);
		statMatchTypeMap.put("components", MatchType.SPELL);
		statMatchTypeMap.put("level", MatchType.SPELL);
		statMatchTypeMap.put("speed", MatchType.MONSTER);
		statMatchTypeMap.put("senses", MatchType.MONSTER);
		statMatchTypeMap.put("armor class", MatchType.MONSTER);
		statMatchTypeMap.put("constitution", MatchType.MONSTER);
		statMatchTypeMap.put("charisma", MatchType.MONSTER);
		statMatchTypeMap.put("dexterity", MatchType.MONSTER);
		statMatchTypeMap.put("intelligence", MatchType.MONSTER);
		statMatchTypeMap.put("wisdom", MatchType.MONSTER);
		statMatchTypeMap.put("initiative", MatchType.MONSTER);
		statMatchTypeMap.put("base attack bonus", MatchType.MONSTER);
		statMatchTypeMap.put("fortitude", MatchType.MONSTER);
		statMatchTypeMap.put("reflex", MatchType.MONSTER);
		statMatchTypeMap.put("willpower", MatchType.MONSTER);
		statMatchTypeMap.put("combat maneuver defense", MatchType.MONSTER);
		statMatchTypeMap.put("experience", MatchType.MONSTER);
		statMatchTypeMap.put("combat maneuver bonus", MatchType.MONSTER);
		statMatchTypeMap.put("environment", MatchType.MONSTER);
		statMatchTypeMap.put("skills", MatchType.MONSTER);
		statMatchTypeMap.put("feats", MatchType.MONSTER);
		statMatchTypeMap.put("melee", MatchType.MONSTER);
		statMatchTypeMap.put("saving throw", MatchType.SPELL);
		statMatchTypeMap.put("organization", MatchType.MONSTER);
		statMatchTypeMap.put("treasure", MatchType.MONSTER);
		statMatchTypeMap.put("special attacks", MatchType.MONSTER);
		statMatchTypeMap.put("target", MatchType.SPELL);
		statMatchTypeMap.put("languages", MatchType.MONSTER);
		statMatchTypeMap.put("immune", MatchType.MONSTER);
		statMatchTypeMap.put("special qualities", MatchType.MONSTER);
		statMatchTypeMap.put("spell-like abilities", MatchType.MONSTER);
		statMatchTypeMap.put("ranged", MatchType.MONSTER);
		statMatchTypeMap.put("resist", MatchType.MONSTER);
		statMatchTypeMap.put("weaknesses", MatchType.MONSTER);
		statMatchTypeMap.put("effect", MatchType.SPELL);
		statMatchTypeMap.put("critical", MatchType.ITEM);
		statMatchTypeMap.put("area", MatchType.SPELL);
		statMatchTypeMap.put("armor check penalty", MatchType.ITEM);
		statMatchTypeMap.put("armor bonus", MatchType.ITEM);
		statMatchTypeMap.put("max dex bonus", MatchType.ITEM);
		statMatchTypeMap.put("arcane spell failure chance", MatchType.ITEM);
	}

	@Override
	public IroriStat getStat(StatRequest statRequest) {
		if(StringUtils.isBlank(statRequest.getStatName()) || StringUtils.isBlank(statRequest.getObjectName())) {
			throw new InvalidInputException();
		}

		// lowercase object and stat names
		statRequest.setObjectName(statRequest.getObjectName().toLowerCase());
		statRequest.setStatName(statRequest.getStatName().toLowerCase());

		// set any stat synonyms to the correct stat name ('A.C.' to 'armor class', etc)
		statRequest.setStatName(textService.getClosestMatch(textService.getSynonym(statRequest.getStatName()), MatchType.STAT));

		// attempt to narrow down match type based on requested stat
		if(statRequest.getObjectType().equalsIgnoreCase(MatchType.OBJECT.toString())) {
			statRequest.setObjectType(getMatchTypeForStat(statRequest.getStatName()).toString());
		}

		// attempt to fix alexa's inability to understand some words by matching input to list of known objects
		statRequest.setObjectName(textService.getClosestMatch(statRequest.getObjectName(), MatchType.valueOf(statRequest.getObjectType().toUpperCase())));

		// see if this object is in our db
		IroriAlias iroriAlias = aliasRepository.findByAlias(statRequest.getObjectName());

		if(iroriAlias == null) {
			// not in the db, throw an exception so we can let the user know
			throw new ObjectNotFoundException(statRequest.getObjectName());
		}

		// the object is in our db, lets see if we know the stat the user is asking for
		IroriStat iroriStat = statRepository.findByObjectAndStatName(iroriAlias.getObject(), statRequest.getStatName());

		if(iroriStat == null) {
			// we don't have the stat in our db, throw an exception to let the user know
			throw new StatNotFoundException(statRequest.getObjectName(), statRequest.getStatName());
		}

		// return the stat
		return iroriStat;
	}

	private MatchType getMatchTypeForStat(String stat) {
		if(statMatchTypeMap.containsKey(stat)) {
			LOG.info(String.format("found MatchType for stat %s: %s", stat, statMatchTypeMap.get(stat)));
			return statMatchTypeMap.get(stat);
		}

		return MatchType.OBJECT;
	}
}
