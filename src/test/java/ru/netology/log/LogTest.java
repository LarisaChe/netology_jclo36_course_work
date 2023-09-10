package ru.netology.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.log.Log;
import ru.netology.log.WorkWithFiles;

import java.io.IOException;
import java.util.List;

public class LogTest {

    Log log = Log.getInstance();
    private static String logFileName = "transfer.log";
    private static String getStringFromLog() {
        List<String> gotList = WorkWithFiles.getListOfStringsFromFile(logFileName);
        String[] arrStr = gotList.get(gotList.size() - 1).split("]");
        return arrStr[1].trim();
    }

    @Test
    public void testLog() throws IOException {
        String testStr = "ТEST: Проверка записи в transfer.log";
        log.log("ТEST","Проверка записи в transfer.log");

        String gotStr = getStringFromLog();
        //Assertions.assertTrue(gotStr.equals(testStr));
        Assertions.assertEquals(testStr, gotStr);
    }

    @Test
    public void testLogInfo() throws IOException {
        log.logInfo("Информационная запись");
        String testStr = "INFO: Информационная запись";

        String gotStr = getStringFromLog();
        Assertions.assertEquals(testStr, gotStr);
    }

    @Test
    public void testError() throws IOException {
        log.logError("Информация об ошибке");
        String testStr = "ERROR: Информация об ошибке";

        String gotStr = getStringFromLog();
        //Assertions.assertTrue(gotStr.equals(testStr));
        Assertions.assertEquals(testStr, gotStr);
    }
}
