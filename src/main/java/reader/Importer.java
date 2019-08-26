package reader;

import validators.ValidationException;

import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Importer {

    private static final String COMMA = ",";
    private static final String DOUBLEQUOTE = "\"";
    private static final String VALID_FILE_ENTRY_REGEX = "^\"[a-zA-Z]+?\",\"[a-zA-Z]+?\",\"\\d{2}\\.\\d{2}\\.\\d{4}\"";
    private static final String OUTPUT_FILE_DIRECTORY = "output_files/";
    private static final String INPUT_FILE_DIRECTORY = "input_files/";
    private static final String DOT = ".";
    public   List<String> inputList = new ArrayList<>();

    public void getListFromCsv(String inputFileName, String targetMonth, String outputFileName) throws IOException, ValidationException {

        // List<String> inputList = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new FileReader(INPUT_FILE_DIRECTORY + inputFileName));
        File outputFolder = new File(OUTPUT_FILE_DIRECTORY);

        validateFolder(outputFolder);

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(new File(OUTPUT_FILE_DIRECTORY + outputFileName)));
        String line;

        while ((line = fileReader.readLine()) != null) {
            inputList.add(line);
        }


        try (fileReader; fileWriter) {
            String output =
                    inputList.stream().filter(s -> isFileEntryValid(s)).filter(s -> monthFilter(s, targetMonth))
                            .sorted(String::compareTo)
                            .map(s1 -> removeDate(s1))
                            .collect(Collectors.joining(System.lineSeparator()));
            fileWriter.write(output);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void validateFolder(File folder) throws ValidationException {
        if (!(folder.exists() && folder.isDirectory())) {
            throw new ValidationException("Output folder '" + OUTPUT_FILE_DIRECTORY + "' not found!");
        }
    }

    private boolean isFileEntryValid(String s) {
        return Pattern.matches(VALID_FILE_ENTRY_REGEX, s);
    }

    private String removeDate(String s) {

        return s.substring(0, s.lastIndexOf(COMMA));
    }

    private boolean monthFilter(String s, String targetMonth) {

        int lastIndexOf = s.lastIndexOf(COMMA);
        String dateAsString = s.substring(lastIndexOf + 1).replaceAll(DOUBLEQUOTE, "");

        return dateAsString.substring(dateAsString.indexOf(DOT) + 1).startsWith(targetMonth);
    }


}
