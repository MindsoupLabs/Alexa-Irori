package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.models.CsvDataImportItem;
import net.mindsoup.irori.models.IroriData;
import net.mindsoup.irori.models.IroriObject;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.services.DataParsingService;
import net.mindsoup.irori.services.TextService;
import net.mindsoup.irori.utils.Constants;
import net.mindsoup.irori.utils.NethysParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Valentijn on 16-7-2017.
 */
@Service
public class DataParsingServiceImpl implements DataParsingService {

	@Autowired
	private TextService textService;

	@Override
	public List<IroriData> parseData(List<CsvDataImportItem> items) {
		return items.stream().map(this::parseItem).collect(Collectors.toList());
	}

	private IroriData parseItem(CsvDataImportItem item) {
		IroriData iroriData = new IroriData();

		IroriObject iroriObject = new IroriObject();
		iroriObject.setName(transformObjectName(item.getName().toLowerCase()));
		iroriData.setObject(iroriObject);

		iroriData.setStats(NethysParser.fromString(item.getContent(), item.getName()));
		IroriStat stat = new IroriStat();
		stat.setStatName(Constants.Stats.SOURCE);
		stat.setStatValue(item.getSource());
		iroriData.getStats().add(stat);

		iroriData.getStats().forEach(e -> e.setStatName(textService.getSynonym(e.getStatName())));

		iroriData.setAliases(textService.getNameAliases(iroriObject.getName()));

		return iroriData;
	}

	private String transformObjectName(String name) {
		// strip trailing i or 1 from names (like summon monster i)
		if(name.endsWith(" i") || name.endsWith(" 1")) {
			name = name.substring(0, name.length() - 2);
		}

		// strip possessive apostrophe from names (like bear's endurance)
		name = name.replace("'","").replace("`","").replace("’", "");

		return name;
	}
}
