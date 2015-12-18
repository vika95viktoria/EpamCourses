package bsu.by.textparser.service;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Виктория on 14.12.2015.
 */
public class TextReader {
    private static Logger logger = Logger.getLogger(TextReader.class);

    public static String readText(String fileName) {
        String textInfo = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            long a = Files.size(Paths.get(fileName));
            char[] b = new char[(int) a];
            in.read(b);
            textInfo = String.valueOf(b);
        } catch (IOException e) {
            logger.error(e);
        }
        return textInfo;
    }
}

