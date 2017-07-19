package net.mindsoup.irori.services;

import net.mindsoup.irori.MatchType;

/**
 * Created by Valentijn on 15-7-2017.
 */
public interface TextService {

	String getSynonym(String name, String objectType);
	String getClosestMatch(String name, MatchType type);
}
