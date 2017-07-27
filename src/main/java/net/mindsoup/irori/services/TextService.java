package net.mindsoup.irori.services;

import net.mindsoup.irori.enums.MatchType;

import java.util.Set;

/**
 * Created by Valentijn on 15-7-2017.
 */
public interface TextService {

	String getSynonym(String name);
	String getClosestMatch(String name, MatchType type);
	Set<String> getNameAliases(String name);
}
