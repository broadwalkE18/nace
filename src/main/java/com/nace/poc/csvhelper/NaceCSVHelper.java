package com.nace.poc.csvhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.nace.poc.dal.NaceData;

@Component
public class NaceCSVHelper {
	private static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<NaceData> csvToNaceData(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<NaceData> naceRecords = new ArrayList<NaceData>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				NaceData naceRecord = new NaceData(csvRecord.get("OrderId"), Integer.parseInt(csvRecord.get("Level")),
						csvRecord.get("Code"), csvRecord.get("Parent"), csvRecord.get("Description"),
						csvRecord.get("ItemIncludes"), csvRecord.get("AlsoIncludes"), csvRecord.get("Rulings"),
						csvRecord.get("Excludes"), csvRecord.get("RefToISICRev4"));

				naceRecords.add(naceRecord);
			}

			return naceRecords;
		} catch (IOException e) {
			throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
		}
	}
}