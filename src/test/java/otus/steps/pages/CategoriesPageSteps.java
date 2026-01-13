package otus.steps.pages;

import com.google.inject.Inject;
import components.popups.AuthPopup;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.WebElement;
import pages.CategoriesPage;
import pages.MainPage;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesPageSteps {

    @Inject
    protected CategoriesPage categoriesPage;
    @Inject
    protected AuthPopup authPopup;
    @Inject
    protected MainPage mainPage;

    @Дано("открыт каталог курсов Otus с фильтром программирования")
    public void givenOpenCatalogWithProgrammingFilter() {
        categoriesPage.open("courses", "categories=programming");
    }

    @Пусть("пользователь ищет курс по заданному имени")
    public void whenUserSearchCourseByName(String courseName) {
        WebElement courseTile = categoriesPage.findCourseByName(courseName);
        courseTile.click();
    }

    @Тогда("страница выбранного курса должна содержать название курса")
    public void thenCheckCoursePageContainsTitle(String courseName) {
        WebElement descriptionElement =

                mainPage.findElementByXpath(".//a[@class='sc-zzdkm7-0 kAwZgf']");

        categoriesPage.waitUntilElementIsVisible(descriptionElement);

        assertThat(descriptionElement.getText()).contains(courseName);
    }

    @И("авторизационное окно не отображается")
    public void andAuthWindowNotDisplayed() {
        authPopup.popupShouldNotBeVisible();
    }
}