package components;

import annotations.Component;
import com.google.inject.Inject;
import commons.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import support.GuiceScoped;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbsComponent extends AbsCommon {

    protected final WebDriver driver;

    @Inject
    public AbsComponent(GuiceScoped guiceScoped, WebDriver driver) {
        super(guiceScoped);
        this.driver = driver;
    }

    {
        assertThat(waiter.waitForCondition(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                Objects.requireNonNull(getByComponent(), "Locator is required for visibility check")))
        ).as("Error loading component").isTrue();
    }

    private By getByComponent() {
        Class<?> clazz = getClass();

        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            String[] componentVal = component.value().split(":");
            switch (componentVal[0].trim()) {
                case "css": return By.cssSelector(componentVal[1]);
                case "xpath": return By.xpath(componentVal[1]);
                default: throw new IllegalArgumentException("Unsupported locator type");
            }
        }
        return null;
    }

    public WebElement getComponentEntry() {
        return $(getByComponent());
    }

    protected void executeScript(String script, Object... args) {
        ((JavascriptExecutor)driver).executeScript(script, args);
    }

    protected WebDriver getDriver() {
        return driver;
    }
}