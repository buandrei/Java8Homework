import reader.Importer;
import validators.ValidationException;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String inputFileName = "input_file.csv";
        String outputFileName = "output1.txt";

        Importer importer = new Importer();
        try {

            importer.getListFromCsv(inputFileName, "05", outputFileName);


        } catch (IOException | ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
