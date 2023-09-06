package ru.netology.log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WorkWithFiles {
    public static List<String> getListOfStringsFromFile(String fileName)  {
        File file = new File(fileName);
        List<String> result = new ArrayList<>();
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String line = reader.readLine();

                while (line != null) {
                    result.add(line);
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void saveToFile(String fn, String str, boolean append) throws IOException {
        try (FileWriter writer = new FileWriter(fn, append)) {
            writer.write(str);
            writer.append('\n');
            writer.flush();
        }
    }
}
