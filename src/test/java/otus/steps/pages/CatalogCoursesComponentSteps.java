package otus.steps.pages;

import com.google.inject.Inject;
import components.CatalogCoursesComponent;
import components.popups.AuthPopup;
import io.cucumber.java.ru.И;
import pages.CategoriesPage;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogCoursesComponentSteps {

    @Inject
    protected CatalogCoursesComponent catalogCoursesComponent;
    @Inject
    protected AuthPopup authPopup;
    @Inject
    protected CategoriesPage categoriesPage;

    @И("Загружена страница каталога курсов")
    public void loadCatalogPage() {

        categoriesPage.open("courses", "categories=programming");

        String courseName = CatalogCoursesComponent.COURSE_NAME;

        categoriesPage.pageHeaderShowIdBySameUs(courseName);

        categoriesPage.clickSelectedCourseByName(courseName)
                .findCourseByName(courseName);

        catalogCoursesComponent.getComponentEntry().click();

        authPopup.popupShouldNotBeVisible()
                .popupShouldBeVisible()
                .closePopup();
    }

    @И("Пользователь выбирает все курсы с совпадающими датами начала")
    public void selectAllMatchingCourses() throws Exception {

        catalogCoursesComponent.verifyEarliestAndLatestCourses();
    }

    @И("Отображены карточки выбранных курсов")
    public void checkDisplayedCards() {
        assertThat(catalogCoursesComponent.getCatalog())
                .as("Каталог пуст.")
                .isNotEmpty();
    }

    @И("Среди выбранных курсов выделяется самый ранний и самый поздний")
    public void highlightEarliestAndLatestCourses() {

        catalogCoursesComponent.highlightEarliestAndLatestCoursesVisually();
    }

    @И("Название и дата старта на карточках указанных курсов отображаются верно")
    public void verifyCourseTitlesAndStartDates() {

        try {
            catalogCoursesComponent.verifyEarliestAndLatestCourses();
        } catch (Exception ex) {
            throw new
                    AssertionError("Ошибка при проверке наименований и дат стартов курсов.", ex);
        }
    }
}