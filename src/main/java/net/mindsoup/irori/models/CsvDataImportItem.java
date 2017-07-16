package net.mindsoup.irori.models;

/**
 * Created by Valentijn on 16-7-2017.
 */
public class CsvDataImportItem {
	private String name;
	private String source;
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
