package utils;

import com.linkedin.test.BaseProgram;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KeywordEngine {
    /***
     * @locatorvalue used to access the value from sheet
     * @locatorName used to access the locators from sheet
     * @workbookfactory used foe creating file and getSheet used for to get sheet name
     * swicth() case used for action's and locator name
     */



    public WebDriver driver;
    public Properties prop;

    public static Workbook workbook;
    public static Sheet sheet;

    public WebElement element;
    public BaseProgram baseProgram;


    public final String SheetPath = "C:\\Users\\Administrator\\eclipse-workspace\\KeyWordDrivenFramework\\src\\test\\resources\\Keyword.xlsx";

    public void startExecution(String SheetName) throws FileNotFoundException {
        String locatorValue = null;
        String locatorName = null;

        FileInputStream file = null;
        try {
            file = new FileInputStream(SheetPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = workbook.getSheet(SheetName);
        int k = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            try {
                String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();//id=sessionkey
                if (!locatorColValue.equalsIgnoreCase("NA")) {
                    locatorName = locatorColValue.split("=")[0].trim();  //id
                    locatorValue = locatorColValue.split("=")[1].trim(); //sessionkey
                }
                String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
                String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

                switch (action) {
                    case "open browser":
                        baseProgram = new BaseProgram();
                        prop = baseProgram.init_properties();
                        if (value.isEmpty() || value.equals("NA")) {
                            driver = baseProgram.init_driver(prop.getProperty("browser"));
                        } else {
                            driver = baseProgram.init_driver(value);
                        }
                        break;

                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {
                            driver.get(prop.getProperty("url"));
                        } else {
                            driver.get(value);
                        }
                        break;
                    case "quit":
                        driver.quit();
                        break;
                    default:
                        break;
                }

                switch (locatorName) {
                    case "id":
                        element = driver.findElement(By.id(locatorValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                            element.clear();
                            element.sendKeys(value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        }
                        //locatorName = null;
                        break;

                    case "type":
                        element = driver.findElement(By.xpath(locatorValue));
                        if (action.equalsIgnoreCase("click")) {
                            element.click();
                        }
                        locatorName = null;
                        break;

                    default:
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
