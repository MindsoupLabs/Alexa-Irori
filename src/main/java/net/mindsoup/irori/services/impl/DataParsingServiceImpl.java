package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.models.CsvDataImportItem;
import net.mindsoup.irori.models.IroriData;
import net.mindsoup.irori.models.IroriObject;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.services.DataParsingService;
import net.mindsoup.irori.utils.Constants;
import net.mindsoup.irori.utils.NethysParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Valentijn on 16-7-2017.
 */
@Service
public class DataParsingServiceImpl implements DataParsingService {
	@Override
	public List<IroriData> parseData(List<CsvDataImportItem> items) {
		return items.stream().map(this::parseItem).collect(Collectors.toList());
	}

	private IroriData parseItem(CsvDataImportItem item) {
		IroriData iroriData = new IroriData();

		IroriObject iroriObject = new IroriObject();
		iroriObject.setName(item.getName().toLowerCase());
		iroriData.setObject(iroriObject);

		iroriData.setStats(NethysParser.fromString(item.getContent()));
		IroriStat stat = new IroriStat();
		stat.setStatName(Constants.Stats.SOURCE);
		stat.setStatValue(item.getSource());
		iroriData.getStats().add(stat);

		return iroriData;
	}
}
