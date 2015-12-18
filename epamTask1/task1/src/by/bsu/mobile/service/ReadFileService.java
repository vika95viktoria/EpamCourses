package by.bsu.mobile.service;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Викторианец on 24.11.2015.
 */
public class ReadFileService {
    static Logger logger = Logger.getLogger(ReadFileService.class);

    public static ArrayList<Integer> readInfo(String fileName) {
        ArrayList<Integer> info = new ArrayList<Integer>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    info.add(Integer.parseInt(s));
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return info;
    }
}
