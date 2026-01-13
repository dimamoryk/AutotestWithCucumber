package factory.settings;

import exceptions.BrowserNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Locale;

public class WebDriverFactory {

    protected String browser = System.getProperty("browser.type").toLowerCase(Locale.ROOT);

    public WebDriver create() {

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions = new ChromeSettings().settings(chromeOptions);
            return new
                    ChromeDriver(chromeOptions);

        } else if ("edge".equals(browser)) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions = new EdgeSettings().settings(edgeOptions);
            return new
                    EdgeDriver(edgeOptions);

        } else {
            throw new
                    BrowserNotSupportedException(browser);

        }
    }

//    public void init() {
//        if ("chrome".equals(browser)) {
//            WebDriverManager.chromedriver().setup();
//        } else if ("edge".equals(browser)) {
//            WebDriverManager.edgedriver().setup();
//        } else {
//            throw new
//                    BrowserNotSupportedException(browser);
//        }
//    }
}
