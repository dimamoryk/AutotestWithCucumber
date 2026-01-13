package otus.steps.pages;

import com.google.inject.Inject;
import components.popups.AuthPopup;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;
import pages.MainPage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MainPageSteps {
    @Inject
    protected MainPage mainPage;
    @Inject
    protected AuthPopup authPopup;


    @Дано("Открыта страница каталога курсов")
    public void openCoursesPage() {
        mainPage.open()
                .pageHeaderShowIdBySameUs("Все ключевые навыки")
                .checkElementContainsText("Все ключевые навыки");

        authPopup.popupShouldBeVisible()
                .closePopup()
                .popupShouldNotBeVisible();
    }

    @Если("Кликнуть по случайному курсу")
    public void clickRandomCourse() {
        mainPage.open("courses", "categories=programming")
                .checkElementContainsText("Java QA Engineer. Professional");

        authPopup.popupShouldBeVisible()
                .closePopup()
                .popupShouldNotBeVisible();

        String title =
                mainPage.getLearningCategory(1);
        mainPage.firstCategoryElement(1);
        mainPage.findElementByXpath(title);
        mainPage.goToLearningSection(title);


    }

    @Тогда("Страница курса успешно открыта")
    public void shouldBeOpenCoursePage() {
        assertThat(mainPage.coursePageOpenedSuccessfully()).isTrue();

    }
}
