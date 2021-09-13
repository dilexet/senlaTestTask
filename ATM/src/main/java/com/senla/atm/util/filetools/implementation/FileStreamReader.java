package com.senla.atm.util.filetools.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileStreamReader {
    private static final String LINE_SEPARATOR = "\n";
    private final Logger logger = LoggerFactory.getLogger(FileStreamReader.class);

    public String[] fileRead(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String s;
            StringBuilder fileData = new StringBuilder();
            while ((s = br.readLine()) != null) {
                fileData.append(s).append("\n");
            }
            return fileData.toString().split(LINE_SEPARATOR);
        } catch (IOException ex) {
            logger.error(ex.toString());
            return null;
        }
    }
}
