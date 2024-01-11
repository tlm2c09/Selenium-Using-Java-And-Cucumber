package utils;

import java.io.*;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class FileUtils {

    public static Map<String, Object> loadYamlConfig(String filePath) {
        YAMLMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkFileExists(String filePath) {
        return new File(filePath).exists();
    }

    public boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    static class CsvFile {
        static CSVReader csvReader;
        static CSVWriter csvWriter;
        public static List<String[]> readAllLinesInCsv(String filePath) {
            try {
                csvReader = new CSVReader(new FileReader(filePath));
                return csvReader.readAll();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }

        public static void writeAllLinesInCsvFile(List<String[]> data, String filePath) {
            try {
                csvWriter = new CSVWriter(new FileWriter(filePath));
                csvWriter.writeAll(data);
                csvWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class ExcelFile {

        public static void readExcelFile(String filePath) {
            FileInputStream fis;
            Workbook workbook;
            try {
                fis = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(fis);
                workbook.sheetIterator().forEachRemaining(sheet ->{
                    for (Row row : sheet) {
                        for (Cell cell : row ){
                            System.out.println(cell);
                        }
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
