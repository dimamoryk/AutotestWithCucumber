package pages;

import annotations.Path;
import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

    @FindBy(xpath = "//div/a[contains(text(), 'Программирование')]")
    protected List<WebElement> categories;

    @FindBy(xpath = "//div[contains(text(), 'Java QA Engineer. Professional')]")
    protected WebElement courseTitle;

    @Inject
    public MainPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }


    public String getLearningCategory(int randomIndex) {
        return categories.get(randomIndex).getText();
    }

    public MainPage goToLearningSection(String title) {
        this.clickElementByPredicate.accept(categories, (WebElement element) ->
                element.getText()
                        .equals(title));
        return this;
    }

    public MainPage checkElementContainsText(String expectedText) {
        WebElement element = driver.findElement(By.id("header"));
        waitUntilElementIsVisible(element);
        assertThat(element.getText())
                .as("Проверяемый элемент не содержит требуемого текста")
                .contains(expectedText);
        return this;
    }

    public WebElement firstCategoryElement(int index) {
        return categories.get(index - 1);
    }

    public WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public boolean coursePageOpenedSuccessfully() {
        try {
            waitUntilElementIsVisible(courseTitle);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
