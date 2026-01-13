package commons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import support.GuiceScoped;
import waiters.Waiter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsCommon {

    public BiConsumer<List<WebElement>, Predicate<WebElement>> clickElementByPredicate =
            (List<WebElement> elements, Predicate<WebElement> elementPredicate) ->
                    elements
                            .stream()
                            .filter(elementPredicate)
                            .findFirst()
                            .orElseThrow(() -> new NoSuchElementException("Without valuable element"))
                            .click();
    protected WebDriver driver;
    protected Actions actions;
    protected Waiter waiter;

    public AbsCommon(GuiceScoped guiceScoped) {
        this.driver = guiceScoped.getDriver();
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
        this.waiter = new Waiter(driver);
    }

    public WebElement $(By selector) {
        return driver.findElement(selector);
    }
}

