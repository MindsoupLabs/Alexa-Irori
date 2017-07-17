package net.mindsoup.irori.services.impl;

import net.mindsoup.irori.models.CsvDataImportItem;
import net.mindsoup.irori.services.CsvFileImportService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Valentijn on 16-7-2017.
 */
@Service
public class CsvFileImportServiceImpl implements CsvFileImportService {
	@Override
	public List<CsvDataImportItem> readCsv(String filename) throws FileNotFoundException {
		// read a CSV file and convert each line to a CsvDataImportItem object
		File inputFile = new File(filename);
		InputStream inputStream = new FileInputStream(inputFile);
		BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));

		return bufferedReader.lines().skip(0).map(mapLineToItem).collect(Collectors.toList());
	}

	private Function<String, CsvDataImportItem> mapLineToItem = (line) -> {
		CsvDataImportItem csvDataImportItem = new CsvDataImportItem();

		// tab separated
		String[] lineItems = line.split("\t");

		csvDataImportItem.setName(lineItems[0].trim());
		csvDataImportItem.setSource(lineItems[1].trim());
		csvDataImportItem.setContent(lineItems[2].trim());

		return csvDataImportItem;
	};
}
