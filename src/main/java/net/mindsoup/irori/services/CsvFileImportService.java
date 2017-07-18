package net.mindsoup.irori.services;

import net.mindsoup.irori.models.CsvDataImportItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Valentijn on 16-7-2017.
 */
public interface CsvFileImportService {

	List<CsvDataImportItem> readCsv(String filename) throws IOException;
}
