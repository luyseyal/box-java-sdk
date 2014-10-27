package com.box.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

final class TestConfig {
    private static Properties configProperties = null;
    private static String accessToken = null;
    private static String refreshToken = null;
    private static String clientID = null;
    private static String clientSecret = null;
    private static String collaborator = null;

    private TestConfig() { }

    public static void setLogLevel(String levelString) {
        Level level = Level.parse(levelString);
        Logger logger = Logger.getLogger("com.box.sdk");
        logger.setLevel(level);

        boolean hasConsoleHandler = false;
        for (Handler handler : logger.getHandlers()) {
            handler.setLevel(level);
            if (handler instanceof ConsoleHandler) {
                hasConsoleHandler = true;
            }
        }

        if (!hasConsoleHandler) {
            Handler handler = new ConsoleHandler();
            handler.setLevel(level);
            logger.addHandler(handler);
        }
    }

    public static String getAccessToken() {
        if (accessToken == null || accessToken.equals("")) {
            accessToken = getProperty("accessToken");
        }

        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TestConfig.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        if (refreshToken == null || refreshToken.equals("")) {
            refreshToken = getProperty("refreshToken");
        }

        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        TestConfig.refreshToken = refreshToken;
    }

    public static String getClientID() {
        if (clientID == null || clientID.equals("")) {
            clientID = getProperty("clientID");
        }

        return clientID;
    }

    public static String getClientSecret() {
        if (clientSecret == null || clientSecret.equals("")) {
            clientSecret = getProperty("clientSecret");
        }

        return clientSecret;
    }

    public static String getCollaborator() {
        if (collaborator == null || collaborator.equals("")) {
            collaborator = getProperty("collaborator");
        }

        return collaborator;
    }

    private static String getProperty(String name) {
        Properties configProperties = loadProperties();
        String value = configProperties.getProperty(name);
        if (value.equals("")) {
            throw new IllegalStateException("The " + name + " property wasn't set in "
                + "src/test/config/config.properties.");
        }

        return value;
    }

    private static Properties loadProperties() {
        if (configProperties != null) {
            return configProperties;
        }

        configProperties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("src/test/config/config.properties");
            configProperties.load(input);
            input.close();
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't open \"src/test/config/config.properties\".", e);
        }

        return configProperties;
    }
}
