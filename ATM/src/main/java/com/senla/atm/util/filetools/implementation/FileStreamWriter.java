package com.senla.atm.util.filetools.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileStreamWriter {
    private final Logger logger = LoggerFactory.getLogger(FileStreamWriter.class);

    public void fileWrite(String path, String data, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            bw.write(data);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
