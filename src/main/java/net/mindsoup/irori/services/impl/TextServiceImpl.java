package net.mindsoup.irori.services.impl;

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

	private Map<String, String> synonyms = null;
	private Map<String, String> matchMappings = new HashMap<>();
	private final Set<String> knownObjectNames;

	public TextServiceImpl() {
		synonyms = initializeSynonyms();
		knownObjectNames = initializeKnownObjectNames();
	}

	@Override
	public String getSynonym(String name, String objectType) {
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

		String closestMatch = getClosestPhoneticMatch(name);

		// add the string to the mappings so we don't need to do it again for this string
		matchMappings.put(name, closestMatch);
		LOG.info(String.format("Added %s to match mappings for %s", name, closestMatch));

		return closestMatch;
	}

	private String getPhoneticMatch(String name) {

		return null;
	}

	private String getClosestPhoneticMatch(String name) {
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

		// find the string with the closest levenshtein distance
		String closestMatch = name;
		int closestLevenshteinDistance = Integer.MAX_VALUE;

		for(String objectName : knownObjectNames) {
			int distance = levenshteinDistance.apply(name, objectName);

			if(distance < closestLevenshteinDistance) {
				closestMatch = objectName;
				closestLevenshteinDistance = distance;
			}
		}

		return closestMatch;
	}

	private Map<String, String> initializeSynonyms() {
		return new HashMap<>();
	}

	private Set<String> initializeKnownObjectNames() {
		HashSet<String> objectNames = new HashSet<>();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("alexa/slottypes/LIST_OF_OBJECTS.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		bufferedReader.lines().forEach(objectNames::add);
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return objectNames;
	}
}
