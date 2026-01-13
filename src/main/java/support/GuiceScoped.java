package support;

import factory.settings.WebDriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

@ScenarioScoped
public class GuiceScoped implements DriverProvider {

    private final WebDriver driver = new WebDriverFactory().create();

    public WebDriver getDriver() {
        return driver;
    }


    @Override
    public WebDriver provideDriver() {
        return driver;
    }
}
