package net.mindsoup.irori.controllers;

import net.mindsoup.irori.models.IroriData;
import net.mindsoup.irori.models.IroriStat;
import net.mindsoup.irori.services.CsvFileImportService;
import net.mindsoup.irori.services.DataParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Valentijn on 16-7-2017.
 */
@RestController
@RequestMapping(value = "/tools", method = RequestMethod.GET)
public class ToolsController {

	@Autowired
	private DataParsingService dataParsingService;

	@Autowired
	private CsvFileImportService csvFileImportService;

	@RequestMapping("/import")
	public String runImport(@RequestParam String filename, @RequestParam String type) throws FileNotFoundException {
		List<IroriData> iroriData = dataParsingService.parseData(csvFileImportService.readCsv(filename));

		StringBuilder stringBuilder = new StringBuilder();

		for(IroriData data : iroriData) {
			stringBuilder.append(String.format("INSERT INTO irori_objects(name, type) VALUES('%s', '%s');\n", data.getObject().getName(), type));

			for(IroriStat stat : data.getStats()) {
				stringBuilder.append(String.format("INSERT INTO irori_stats(object, stat, value) VALUES( (SELECT id FROM irori_objects WHERE name = '%s'), '%s', '%s');\n", data.getObject().getName(), stat.getStatName(), stat.getStatValue()));
			}
		}

		return stringBuilder.toString();
	}
}
