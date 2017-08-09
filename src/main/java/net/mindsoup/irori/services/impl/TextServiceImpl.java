package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.enums.MatchType;
import net.mindsoup.irori.services.TextService;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
		matchesAndMappingsMap.put(MatchType.FORMULA, new MatchesAndMappings(MatchType.FORMULA, getNamesFromFile("alexa/irori/slottypes/LIST_OF_FORMULAS.txt")));
		matchesAndMappingsMap.put(MatchType.SPELL, new MatchesAndMappings(MatchType.SPELL, getNamesFromFile("alexa/spellfinder/slottypes/LIST_OF_SPELLS.txt")));
		matchesAndMappingsMap.put(MatchType.MONSTER, new MatchesAndMappings(MatchType.MONSTER, getNamesFromFile("alexa/monsterfinder/slottypes/LIST_OF_MONSTERS.txt")));
		matchesAndMappingsMap.put(MatchType.ITEM, new MatchesAndMappings(MatchType.ITEM, getNamesFromFile("alexa/itemfinder/slottypes/LIST_OF_ITEMS.txt")));
	}

	@Override
	public String getSynonym(String name) {
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
		String closestMatch = getClosestPhoneticMatch(name, matchesAndMappings.getKnownNames());

		// add the string to the mappings so we don't need to do it again for this string
		matchesAndMappings.getMatchMappings().put(name, closestMatch);
		LOG.info(String.format("Added %s to match mappings for %s", name, closestMatch));

		return closestMatch;
	}

	@Override
	public Set<String> getNameAliases(String name) {
		Set<String> aliases = new HashSet<>();

		name = stripBracketsFromName(name);

		aliases.add(name);

		// only add this alias if it's different from the name
		String alias = getCommaAlias(name);
		if(!alias.equals(name)) {
			aliases.add(alias);
		}

		// only add this alias if it's different from the name
		alias = getEyeAlias(alias);
		if(!alias.equals(name)) {
			aliases.add(alias);
		}

		return aliases;
	}

	private String getClosestPhoneticMatch(String name, Collection<String> matchList) {
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

		// find the string with the closest levenshtein distance
		int closestLevenshteinDistance = name.length();

		// store found matches here
		LinkedList<String> foundMatches = new LinkedList<>();
		foundMatches.add(name);

		for(String objectName : matchList) {
			int distance = levenshteinDistance.apply(name, objectName);

			// if we've found a match
			if(distance <= closestLevenshteinDistance) {
				// the match is a closer match than the last match
				if (distance < closestLevenshteinDistance) {
					// throw all existing matches away
					foundMatches.clear();
					closestLevenshteinDistance = distance;
				}

				// add this match
				foundMatches.add(objectName);
			}
		}

		if(foundMatches.size() > 1) {
			LOG.info(String.format("%s matches found for %s with levenshtein distance of %s: %s", foundMatches.size(), name,  closestLevenshteinDistance, foundMatches.toString()));
			return getClosestDoubleMetaphoneMatch(name, foundMatches);
		}

		LOG.info(String.format("found match for %s with levenshtein distance %s: %s", name, closestLevenshteinDistance, foundMatches.getFirst()));
		return foundMatches.getFirst();
	}

	private String getClosestDoubleMetaphoneMatch(String original, LinkedList<String> possibilities) {
		DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
		doubleMetaphone.setMaxCodeLen(original.length());

		// set up map with the double metaphone value of each possibility as key, and the possibility as value
		Map<String, String> encodedPossibilitiesMap = possibilities.stream().collect(Collectors.toMap(doubleMetaphone::encode, Function.identity()));

		LOG.info(String.format("looking for double metaphone matches of %s in %s", original, possibilities.toString()));
		// get the closest double metaphone match
		String closestMetaphoneMatch = getClosestPhoneticMatch(doubleMetaphone.encode(original), encodedPossibilitiesMap.keySet());

		// if we found a double metaphone match, return the corresponding value
		if(encodedPossibilitiesMap.containsKey(closestMetaphoneMatch)) {
			LOG.info(String.format("double metaphone match %s found for %s", encodedPossibilitiesMap.get(closestMetaphoneMatch), original));
			return encodedPossibilitiesMap.get(closestMetaphoneMatch);
		}

		// nothing apparently matches - this is very unusual
		LOG.info(String.format("no double metaphone match found for %s in list %s, returning %s", original, possibilities.toString(), possibilities.getFirst()));
		return possibilities.getFirst();
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
		synonyms.put("saving", "saving throw");
		synonyms.put("save", "saving throw");

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

	private String stripBracketsFromName(String name) {
		return name.replaceAll("\\(.+\\)", "").trim();
	}

	private String getCommaAlias(String name) {
		// if this name does not contain a comma, early out
		if(!name.contains(",")) {
			return name;
		}

		// split the name in two pieces on the comma
		String[] splitResults = name.split(",");

		splitResults[0] = splitResults[0].trim();
		splitResults[1] = splitResults[1].trim();

		// reverse places, so "restoration, lesser" becomes "lesser restoration"
		String result = String.format("%s %s", splitResults[1], splitResults[0]);

		// if the second part of the name contains the first part (for example "Giant, Rune Giant")
		// just ignore the first part entirely
		if(splitResults[1].contains(splitResults[0])) {
			result = splitResults[1];
		}

		return result;
	}

	/**
	 * Add an alias for words containing the word 'eye', which alexa often interprets as 'I', or 'eyes' as 'ice'
	 */
	private String getEyeAlias(String name) {
		// if this name does not contain 'eye', early out
		if(!name.contains("eye")) {
			return name;
		}

		// eyes to ice
		if(name.contains("eyes")) {
			name = name.replace("eyes", "ice");
		}

		// eye to I
		Pattern pattern = Pattern.compile("(.*)eye(.*)");
		Matcher matcher = pattern.matcher(name);

		while(matcher.find()) {
			// reconstruct name, with eye replaced as i

			String replacement = "i";
			if(matcher.group(1).matches(".*[a-zA-Z]")) {
				replacement = " " + replacement;
			}

			if(matcher.group(2).matches("[a-zA-Z].*")) {
				replacement = replacement + " ";
			}

			name = matcher.group(1) + replacement + matcher.group(2);
		}

		return name;
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
