package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.MatchType;
import net.mindsoup.irori.services.TextService;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Valentijn on 15-7-2017.
 */
@Service
public class TextServiceImpl implements TextService {

	private static final Logger LOG = LoggerFactory.getLogger(TextServiceImpl.class);

	private final Map<String, String> synonyms;
	private final Map<MatchType, MatchesAndMappings> matchesAndMappingsMap = new HashMap<>();

	public TextServiceImpl() {
		synonyms = initializeSynonyms();
		matchesAndMappingsMap.put(MatchType.OBJECT, new MatchesAndMappings(MatchType.OBJECT, getNamesFromFile("alexa/irori/slottypes/LIST_OF_OBJECTS.txt")));
		matchesAndMappingsMap.put(MatchType.STAT, new MatchesAndMappings(MatchType.OBJECT, getNamesFromFile("alexa/irori/slottypes/LIST_OF_STATS.txt")));
		matchesAndMappingsMap.put(MatchType.FORMULA, new MatchesAndMappings(MatchType.FORMULA, getNamesFromFile("alexa/slottypes/LIST_OF_FORMULAS.txt")));
		matchesAndMappingsMap.put(MatchType.SPELL, new MatchesAndMappings(MatchType.SPELL, getNamesFromFile("alexa/spellfinder/slottypes/LIST_OF_SPELLS.txt")));
		matchesAndMappingsMap.put(MatchType.MONSTER, new MatchesAndMappings(MatchType.MONSTER, getNamesFromFile("alexa/monsterfinder/slottypes/LIST_OF_MONSTERS.txt")));
		matchesAndMappingsMap.put(MatchType.ITEM, new MatchesAndMappings(MatchType.ITEM, getNamesFromFile("alexa/itemfinder/slottypes/LIST_OF_ITEMS.txt")));
	}

	@Override
	public String getSynonym(String name, String objectType) {
		if(synonyms.containsKey(name)) {
			return synonyms.get(name);
		}

		return name;
	}

	@Override
	public String getClosestMatch(String name, MatchType type) {
		MatchesAndMappings matchesAndMappings = matchesAndMappingsMap.get(type);

		if(matchesAndMappings == null) {
			return name;
		}

		// first make sure it's not an exact match already
		if(matchesAndMappings.getKnownNames().contains(name)) {
			return name;
		}

		// check if we may have encountered this before
		if(matchesAndMappings.getMatchMappings().containsKey(name)) {
			return matchesAndMappings.getMatchMappings().get(name);
		}

		// have not encountered this before and it's not a known object, so let's see if we can find a match
		String closestMatch = getClosestPhoneticMatch(name, type);

		// add the string to the mappings so we don't need to do it again for this string
		matchesAndMappings.getMatchMappings().put(name, closestMatch);
		LOG.info(String.format("Added %s to match mappings for %s", name, closestMatch));

		return closestMatch;
	}

	private String getClosestPhoneticMatch(String name, MatchType type) {
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

		// find the string with the closest levenshtein distance
		String closestMatch = name;
		int closestLevenshteinDistance = name.length();

		for(String objectName : matchesAndMappingsMap.get(type).getKnownNames()) {
			int distance = levenshteinDistance.apply(name, objectName);

			if(distance < closestLevenshteinDistance || (distance == closestLevenshteinDistance && objectName.contains(name))) {
				closestMatch = objectName;
				closestLevenshteinDistance = distance;
			}
		}

		return closestMatch;
	}

	private Map<String, String> initializeSynonyms() {
		Map<String, String> synonyms = new HashMap<>();

		synonyms.put("xp", "experience");
		synonyms.put("XP", "experience");
		synonyms.put("init", "initiative");
		synonyms.put("ac", "armor class");
		synonyms.put("fort", "fortitude");
		synonyms.put("ref", "reflex");
		synonyms.put("will", "willpower");
		synonyms.put("str", "strength");
		synonyms.put("dex", "dexterity");
		synonyms.put("con", "constitution");
		synonyms.put("int", "intelligence");
		synonyms.put("wis", "wisdom");
		synonyms.put("cha", "charisma");
		synonyms.put("base atk", "base attack bonus");
		synonyms.put("cmb", "combat maneuver bonus");
		synonyms.put("cmd", "combat maneuver defense");
		synonyms.put("sq", "special qualities");
		synonyms.put("hp", "hit points");
		synonyms.put("sr", "spell resistance");
		synonyms.put("dr", "damage reduction");
		synonyms.put("cl", "caster level");

		return synonyms;
	}

	private Set<String> getNamesFromFile(String filename) {
		HashSet<String> objectNames = new HashSet<>();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		bufferedReader.lines().forEach(objectNames::add);
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return objectNames;
	}

	private class MatchesAndMappings {
		private final MatchType type;
		private final Map<String, String> matchMappings = new HashMap<>();
		private final Set<String> knownNames;

		public MatchesAndMappings(MatchType type, Set<String> names) {
			this.type = type;
			this.knownNames = names;
		}

		public MatchType getType() {
			return type;
		}

		public Map<String, String> getMatchMappings() {
			return matchMappings;
		}

		public Set<String> getKnownNames() {
			return knownNames;
		}
	}
}
