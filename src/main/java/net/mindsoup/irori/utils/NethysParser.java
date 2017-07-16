package net.mindsoup.irori.utils;

import net.mindsoup.irori.models.IroriStat;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
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

		while (matcher.find()) {
			IroriStat iroriStat = new IroriStat();
			iroriStat.setStatName(transformStatName(matcher.group(1).trim().toLowerCase()));
			iroriStat.setStatValue(matcher.group(2).trim().toLowerCase());

			if(StringUtils.isNoneBlank(iroriStat.getStatName(), iroriStat.getStatValue())) {
				stats.add(iroriStat);
			}
		}
		return stats;
	}

	public static String transformStatName(String stat) {
		switch (stat) {
			// some spells have 'target or targets' instead of just 'target'
			case "target or targets":
				return Constants.Stats.TARGET;
		}

		return stat;
	}
}
