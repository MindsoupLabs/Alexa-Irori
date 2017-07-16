package net.mindsoup.irori.utils;

import net.mindsoup.irori.models.IroriStat;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class NethysParser {

	public static List<IroriStat> fromString(String nethysContent) {
		List<IroriStat> stats = new LinkedList<>();

		int mythicIndex = nethysContent.indexOf(Constants.Keywords.MYTHIC);

		if(mythicIndex > -1) {
			nethysContent = nethysContent.substring(0, mythicIndex);
		}

		final Pattern pattern = Pattern.compile("b>([^<]+)</b>(.+?)<");
		Matcher matcher = pattern.matcher(nethysContent);

		Set<String> addedStats = new HashSet<>();
		while (matcher.find()) {
			IroriStat iroriStat = new IroriStat();
			iroriStat.setStatName(transformStatName(matcher.group(1).trim().toLowerCase()));
			iroriStat.setStatValue(matcher.group(2).trim());

			// only add stats that have non-empty stats and values
			// only add stats that aren't accidental parsings of tables or other html constructs (contains value '<')
			// only add stats we haven't added before
			if(StringUtils.isNoneBlank(iroriStat.getStatName(), iroriStat.getStatValue()) && !iroriStat.getStatValue().contains("<") && !addedStats.contains(iroriStat.getStatName())) {
				stats.add(iroriStat);
				addedStats.add(iroriStat.getStatName());

			}
		}
		return stats;
	}

	public static String transformStatName(String stat) {
		switch (stat) {
			// some spells have 'target or targets' instead of just 'target'
			case "target or targets":
				return Constants.Stats.TARGET;
			case "targt":
				return Constants.Stats.TARGET;
			case "target or area":
				return Constants.Stats.TARGET;
			case "target, effect, or area":
				return Constants.Stats.TARGET;
			case "target/effect":
				return Constants.Stats.TARGET;
			case "targer":
				return Constants.Stats.TARGET;
			case "target, effect, area":
				return Constants.Stats.TARGET;
			case "area or target":
				return Constants.Stats.TARGET;
			case "targets":
				return Constants.Stats.TARGET;
			case "hp":
				return Constants.Stats.HITPOINTS;
		}

		return stat;
	}
}
