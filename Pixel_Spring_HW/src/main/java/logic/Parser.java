package logic;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import Exceptions.CsvParsingException;
import net.sf.jsefa.Deserializer;
import net.sf.jsefa.common.converter.DateConverter;
import net.sf.jsefa.common.converter.LongConverter;
import net.sf.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import net.sf.jsefa.csv.CsvIOFactory;
import net.sf.jsefa.csv.config.CsvConfiguration;

@Service
public class Parser {
		
	public static Deserializer getSimpleCSVdeserializer(Class<?> clazz, char delimiter) {
			
			CsvConfiguration cnfg = new CsvConfiguration();
			cnfg.setFieldDelimiter(delimiter);		
			cnfg.setLineFilter(new HeaderAndFooterFilter(1, false, true));

			cnfg.getSimpleTypeConverterProvider().registerConverterType(Long.class, LongConverter.class);
			cnfg.getSimpleTypeConverterProvider().registerConverterType(Date.class, DateConverter.class);
					
		return CsvIOFactory.createFactory(cnfg, clazz).createDeserializer();
	}
	

	public static <T> List<T> parseCSVtoList(File file, List<T> list, Deserializer deserializer)
			throws IOException, CsvParsingException {
		
		String source = Files.readString(file.toPath());
		source = source.toUpperCase();

		if (source.isBlank())
			throw new RuntimeException("source file was blank");
		
		StringReader reader = new StringReader(source);

		try {

			deserializer.open(reader);

			while (deserializer.hasNext()) {
				list.add(deserializer.next());
			}

		} catch (RuntimeException e) {
			
			throw new CsvParsingException(
					"Ex during parsing file: " + file.getPath() + " checkout file and its Entity class ", e);
			
		} finally {
			
			deserializer.close(true);
		}

		return list;
	}
	
	
	

	public static <T> void putCSVdataToBase(File file, List<T> list, JpaRepository<T, Long> dao, Deserializer deserializer)
			throws IOException, CsvParsingException {

		List<T> results = parseCSVtoList(file, list, deserializer);

		dao.saveAllAndFlush(results);

	}

}
