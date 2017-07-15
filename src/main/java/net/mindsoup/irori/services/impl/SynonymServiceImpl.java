package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.services.SynonymService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Valentijn on 15-7-2017.
 */
@Service
public class SynonymServiceImpl implements SynonymService {

	private Map<String, String> synonyms = null;

	@Override
	public String getSynonym(String name) {
		ensureSynonyms();

		name = name.toLowerCase();

		if(synonyms.containsKey(name)) {
			return synonyms.get(name);
		}

		return name;
	}

	private void ensureSynonyms() {
		if(synonyms == null) {
			initializeSynonyms();
		}
	}

	private void initializeSynonyms() {
		synonyms = new HashMap<>();
	}
}
