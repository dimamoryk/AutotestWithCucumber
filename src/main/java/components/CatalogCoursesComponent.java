package components;

import annotations.Component;
import com.google.inject.Inject;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component("xpath://a[@class='sc-zzdkm7-0 kAwZgf']")
public class CatalogCoursesComponent extends AbsComponent {

    public static final String COURSE_NAME = "Java QA Engineer. Professional";

    @FindBy(xpath = "//a[@class='sc-zzdkm7-0 kAwZgf'][./descendant::*[normalize-space(text())='Java QA Engineer. Professional']]")
    protected List<WebElement> catalog;

    @Inject
    public CatalogCoursesComponent(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    // Вспомогательный метод для фильтрации элементов по заданному предикату
    public <T> T findElementByPredicate(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Без нужного элемента"));
    }

    // Основной метод проверки ранних и поздних курсов
    public void verifyEarliestAndLatestCourses() throws Exception {
        List<WebElement> catalog = this.catalog;

        // Ищем самый ранний курс
        WebElement earliestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(
                        catalog.stream()
                                .map(webElement -> webElement
                                        .getAttribute("data-start-date"))
                                .collect(Collectors.minBy(String::compareTo))
                                .orElse(null)
                ));

        // Ищем самый поздний курс
        WebElement latestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(
                        catalog.stream()
                                .map(webElement -> webElement
                                        .getAttribute("data-start-date"))
                                .collect(Collectors.maxBy(String::compareTo))
                                .orElse(null)
                ));

        // Проверяем оба курса
        verifyCourseDetailsUsingJsoup(earliestCourse);
        verifyCourseDetailsUsingJsoup(latestCourse);
    }

    // Метод проверки деталей курса с использованием Jsoup
    private void verifyCourseDetailsUsingJsoup(WebElement course) throws Exception {
        if (course != null) {
            Document doc = Jsoup.connect(course.getAttribute("href")).get();

            Elements titles = doc.select(".course-title");
            Elements startDates = doc.select(".start-date");

            assertThat(titles.first().text())
                    .withFailMessage("Название курса неверно.")
                    .isEqualTo(course.getText());

            assertThat(DateUtils.parseDate(startDates.first().text()))
                    .withFailMessage("Дата начала курса неверна.")
                    .isEqualTo(DateUtils.parseDate(course.getAttribute("data-start-date")));
        }
    }
}