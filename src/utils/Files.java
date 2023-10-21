package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Files {
    public static List<String> getUrlsFromTextFile(File path) throws FileNotFoundException {
        Scanner reader = new Scanner(path);
        List<String> games = new ArrayList<>();

        while (reader.hasNextLine()) {
            games.add(reader.nextLine());
        }

        reader.close();

        return games;
    }

    public static Map<String, String> getIniFile(File path) throws FileNotFoundException {
        Scanner reader = new Scanner(path);
        Map<String, String> result = new HashMap<>();

        while (reader.hasNextLine()) {
            String[] split = reader.nextLine().split("=");

            String key = split[0].strip();
            String[] valueParts = Arrays.copyOfRange(split, 1, split.length);
            String value = String.join("", valueParts).strip();

            result.put(key, value);
        }

        return result;
    }
}
