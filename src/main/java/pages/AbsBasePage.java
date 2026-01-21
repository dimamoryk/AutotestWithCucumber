package pages;

import annotations.*;
import com.google.inject.Inject;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.GuiceScoped;

import java.time.Duration;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbsBasePage<T> extends AbsCommon {

    protected String baseUrl = System.getProperty("base.url");

    @FindBy(xpath = "//div[text()='Все ключевые навыки']")
    protected WebElement header;

    @Inject
    public AbsBasePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
        PageFactory.initElements(driver, this);
    }

    protected String getPath() {
        Class<T> clazz = (Class<T>) this.getClass();
        if (clazz.isAnnotationPresent(Path.class)) {
            return clazz.getDeclaredAnnotation(Path.class).value();
        }
        throw new PathNotFoundException();
    }

    public T open() {
        driver.get(baseUrl + getPath());
        return (T) this;
    }

    public T open(String name, String... data) {
        driver.get(baseUrl + getPathWithData(name, data));
        return (T) this;
    }

    protected String getPathWithData(String name, String... data) {
        Class<T> clazz = (Class<T>) this.getClass();
        UrlTemplate urlTemplate = null;

        if (clazz.isAnnotationPresent(Urls.class)) {
            Urls urls = clazz.getDeclaredAnnotation(Urls.class);
            urlTemplate = Arrays.stream(urls.urlTemplate())
                    .filter(template -> template.name().equals(name))
                    .findFirst()
                    .orElse(null);
        }

        if (urlTemplate == null && clazz.isAnnotationPresent(UrlTemplate.class)) {
            urlTemplate = clazz.getDeclaredAnnotation(UrlTemplate.class);
        }

        if (urlTemplate != null) {
            String template = urlTemplate.value();
            for (int i = 0; i < data.length; i++) {
                template = template.replace(String.format("{%d}", i + 1), data[i]);
            }
            return template;
        }

        return "";
    }

    public T checkElementContainsText(WebElement element, String expectedText) {
        assertThat(element.getText()).contains(expectedText);
        return (T) this;
    }

    public T waitUntilElementIsVisible(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException ex) {
            throw new TimeoutException("Element '" + element + "' is still invisible.", ex);
        }
        return (T) this;
    }

    public T pageHeaderShowIdBySameUs(String header) {
        assertThat(this.header.getText())
                .as("Error")
                .isEqualTo(header);
        return (T) this;
    }
}