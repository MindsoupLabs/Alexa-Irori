package net.mindsoup.irori.services;

import net.mindsoup.irori.models.CsvDataImportItem;
import net.mindsoup.irori.models.IroriData;

import java.util.List;

/**
 * Created by Valentijn on 16-7-2017.
 */
public interface DataParsingService {

	List<IroriData> parseData(List<CsvDataImportItem> items, String type);
}
