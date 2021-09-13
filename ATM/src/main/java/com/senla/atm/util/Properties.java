package com.senla.atm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class Properties {
    private static final String PROPERTY_FILE_NAME = "app.properties";
    private static final String ERROR_PROPERTY_NOT_FOUND = "Property not found";


    private static Properties properties = null;
    private final Logger logger = LoggerFactory.getLogger(Properties.class);


    public static Properties getInstance() {
        if (properties == null) {
            properties = new Properties();
        }
        return properties;
    }

    public String getProperty(String propertyName) {
        java.util.Properties props = new java.util.Properties();
        try {
            props.load(new FileInputStream(PROPERTY_FILE_NAME));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        String property = props.getProperty(propertyName);
        if (property == null) {
            logger.error(ERROR_PROPERTY_NOT_FOUND);
        }
        return property;
    }
}
