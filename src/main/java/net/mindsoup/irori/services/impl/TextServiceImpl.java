package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.services.TextService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	private Map<String, String> synonyms = null;
	private Map<String, String> matchMappings = new HashMap<>();
	private final Set<String> knownObjectNames;

	public TextServiceImpl() {
		synonyms = initializeSynonyms();
		knownObjectNames = initializeKnownObjectNames();
	}

	@Override
	public String getSynonym(String name, String objectType) {
		name = name.toLowerCase();

		if(synonyms.containsKey(name)) {
			return synonyms.get(name);
		}

		return name;
	}

	@Override
	public String getClosestMatch(String name) {
		// first make sure it's not an exact match already
		if(knownObjectNames.contains(name)) {
			return name;
		}

		// check if we may have encountered this before
		if(matchMappings.containsKey(name)) {
			return matchMappings.get(name);
		}


		String closestMatch = getLevensteinMatch(name);

		// add the string to the mappings so we don't need to do it again for this string
		matchMappings.put(name, closestMatch);
		LOG.info(String.format("Added %s to levenshtein mappings for %s", name, closestMatch));

		return closestMatch;
	}

	private String getPhoneticMatch(String name) {
		DoubleMetaphone doubleMetaPhone = new DoubleMetaphone();
		return null;
	}

	@SuppressWarnings("deprecated")
	private String getLevensteinMatch(String name) {
		// find the string with the closest levenshtein distance
		String closestMatch = name;
		int closestLevenshteinDistance = Integer.MAX_VALUE;
		for(String objectName : knownObjectNames) {
			int levenshteinDistance = StringUtils.getLevenshteinDistance(name, objectName);

			if(levenshteinDistance < closestLevenshteinDistance) {
				closestMatch = objectName;
				closestLevenshteinDistance = levenshteinDistance;
			}
		}

		return closestMatch;
	}

	private Map<String, String> initializeSynonyms() {
		return new HashMap<>();
	}

	private Set<String> initializeKnownObjectNames() {
		return new HashSet<>();
	}
}
