package by.zinkov.victor.dao.impl.pool;

import by.zinkov.victor.dao.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DBConfigurator {
    public Properties getProperties(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new ConnectionException("cannot find property file!", e);
        }
    }
}
