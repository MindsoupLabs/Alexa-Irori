package net.mindsoup.irori.utils;

import net.mindsoup.irori.models.IroriStat;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class NethysParser {

	public static List<IroriStat> fromString(String nethysContent, String title) {
		List<IroriStat> stats = new LinkedList<>();

		// strip mythic content
		int mythicIndex = nethysContent.indexOf(Constants.Keywords.MYTHIC);
		if(mythicIndex > -1) {
			nethysContent = nethysContent.substring(0, mythicIndex);
		}

		// strip 'creating a x' content
		int creatingIndex = nethysContent.indexOf(Constants.Keywords.CREATING_A);
		if(creatingIndex > -1) {
			nethysContent = nethysContent.substring(0, creatingIndex);
		}

		// strip everything before the objects title
		if(StringUtils.isNotBlank(title)) {
			int titleIndex = nethysContent.indexOf(String.format("<h1 class=\"title\">%s</h1>",title));
			if(titleIndex > -1) {
				nethysContent = nethysContent.substring(titleIndex);
			}
		}

		// strip any text with an h1 header after our stat block
		Pattern pattern = Pattern.compile(String.format("(<h1 class=\"title\">%s</h1>.+?)<h1",title));
		Matcher matcher = pattern.matcher(nethysContent);
		while (matcher.find()) {
			nethysContent = matcher.group(1);
		}

		// stats
		pattern = Pattern.compile("b>([^<]+)</b>(.+?)<");
		matcher = pattern.matcher(nethysContent);
		Set<String> addedStats = new HashSet<>();
		while (matcher.find()) {
			IroriStat iroriStat = new IroriStat();
			iroriStat.setStatName(transformStatName(matcher.group(1).trim().toLowerCase()));
			iroriStat.setStatValue(transformValue(matcher.group(2).trim()));

			// only add stats that have non-empty stats and values
			// only add stats that aren't accidental parsings of tables or other html constructs (contains value '<')
			// only add stats we haven't added before
			if(StringUtils.isNoneBlank(iroriStat.getStatName(), iroriStat.getStatValue()) && !iroriStat.getStatValue().contains("<") && !addedStats.contains(iroriStat.getStatName())) {
				stats.add(iroriStat);
				addedStats.add(iroriStat.getStatName());

			}
		}

		// description
		pattern = Pattern.compile(">Description</h3>(.+?)(</span>|<h|$)");
		matcher = pattern.matcher(nethysContent);
		while (matcher.find()) {
			IroriStat iroriStat = new IroriStat();
			iroriStat.setStatName(Constants.Stats.DESCRIPTION);
			iroriStat.setStatValue(transformValue(matcher.group(1).trim()));

			if(StringUtils.isNotBlank(iroriStat.getStatValue())) {
				stats.add(iroriStat);
			}
		}

		// requirements
		pattern = Pattern.compile("b>Requirements</b>(.+?)<(h3|b)");
		matcher = pattern.matcher(nethysContent);
		while (matcher.find()) {
			IroriStat iroriStat = new IroriStat();
			iroriStat.setStatName(Constants.Stats.REQUIREMENTS);
			iroriStat.setStatValue(transformValue(matcher.group(1).trim()));

			if(StringUtils.isNotBlank(iroriStat.getStatValue())) {
				// remove previous requirements stat if it exists
				stats = stats.stream().filter(e -> !e.getStatName().equals(Constants.Stats.REQUIREMENTS)).collect(Collectors.toList());

				// add this one
				stats.add(iroriStat);
			}
		}

		return stats;
	}

	private static String unifyQuotes(String string) {
		return string.replace("`", "'").replace("’", "'");
	}

	private static String transformHtmlString(String description) {
		return Jsoup.parse(description).text();
	}

	private static String transformValue(String value) {
		return unifyQuotes(transformHtmlString(value.replace("/level", " per level").replace("—", "none").replace("—/—", "none")));
	}

	private static String transformStatName(String stat) {
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
		}

		return stat;
	}
}
