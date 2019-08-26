package reader;

import org.junit.Before;
import org.junit.Test;
import validators.ValidationException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ImporterTest {
    Importer importer;


    @Before
    public void setup() {
        importer = new Importer();


    }

    @Test
    public void testThatImputLinesWork() throws IOException, ValidationException {
        importer.getListFromCsv("test1.txt", "08", "outputTest1.txt");
        assertThat(importer.inputList.size(),is(2));
    }

}