package utils;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils extends LoggerUtils{

    private static final String sPropertiesFilePath = "common.properties";

    public static Properties loadPropertiesFile(String sFilePath) {

        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(sFilePath);
        Properties properties = new Properties();
        try{
            properties.load(inputStream);
        } catch (IOException e) {
            Assert.fail("Cannot load " + sFilePath + " file. Message: " + e.getMessage());
        }
        return properties;
    }

    private static Properties loadPropertiesFile() {
        return loadPropertiesFile(sPropertiesFilePath);
    }

    private static String getProperty(String sProperty) {
        Properties properties = loadPropertiesFile();
        String sResult = properties.getProperty(sProperty);
        Assert.assertNotNull(sResult, "Cennot find property " + sProperty + " in properties file");
        return sResult;
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getDriversFolder() {
        return getProperty("driversFolder");
    }
}
