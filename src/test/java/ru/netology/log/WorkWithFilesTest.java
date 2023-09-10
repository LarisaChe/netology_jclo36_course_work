package ru.netology.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.log.WorkWithFiles;

import java.util.Arrays;
import java.util.List;

public class WorkWithFilesTest {

    @BeforeEach
    public void testSaveToFile() {
        Assertions.assertDoesNotThrow(() -> WorkWithFiles.saveToFile("test.txt", "Hello, world!", false));
    }

    @Test
    public void testGetListOfStringsFromFile() {
        List<String> testStr = Arrays.asList("Hello, world!");
        List<String> gotStr = WorkWithFiles.getListOfStringsFromFile("test.txt");
        Assertions.assertTrue(gotStr.equals(testStr));

    }
}
