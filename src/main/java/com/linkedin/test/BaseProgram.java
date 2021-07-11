package com.linkedin.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseProgram {

    public WebDriver driver;
    public Properties prop;

    public WebDriver init_driver(String  browserName){
        if(browserName.equals("chrome")){
            WebDriverManager.chromedriver().setup();
            if(prop.getProperty("headless").equals("yes")){
                //headless mode
                ChromeOptions options= new ChromeOptions();
                options.addArguments("--headless");
                driver = new ChromeDriver(options);
            }else {
                driver = new ChromeDriver();
            }
        }
        return driver;
    }

    public Properties init_properties() throws FileNotFoundException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\eclipse-workspace\\KeyWordDrivenFramework\\src\\main\\java\\utils\\config.properties");
        try {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
