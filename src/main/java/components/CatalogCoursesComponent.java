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
import java.util.Optional;
import java.util.function.Predicate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Component("xpath://a[@class='sc-zzdkm7-0 kAwZgf']")
public class CatalogCoursesComponent extends AbsComponent {

    public static final String COURSE_NAME = "Java QA Engineer. Professional";

    @FindBy(xpath = "//a[@class='sc-zzdkm7-0 kAwZgf'][./descendant::*[normalize-space(text())='Java QA Engineer. Professional']]")
    protected List<WebElement> catalog;
    @Inject
    public CatalogCoursesComponent(GuiceScoped guiceScoped) {
        super(guiceScoped, guiceScoped.provideDriver());
    }
    public List<WebElement> getCatalog() {
        return catalog;
    }

    public <T> Optional<T> findElementByPredicate(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst();
    }
    public void verifyEarliestAndLatestCourses() throws Exception {
        List<WebElement> catalogList = this.catalog;

        Optional<String> minStartDateOpt = catalogList.stream()
                .map(e -> e.getAttribute("data-start-date"))
                .min(String::compareTo);

        Optional<String> maxStartDateOpt = catalogList.stream()
                .map(e -> e.getAttribute("data-start-date"))
                .max(String::compareTo);

        WebElement earliestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(minStartDateOpt.orElse(null)))
                .orElseThrow(() -> new NoSuchElementException("Ранний курс не найден"));

        WebElement latestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(maxStartDateOpt.orElse(null)))
                .orElseThrow(() -> new NoSuchElementException("Поздний курс не найден"));

        verifyCourseDetailsUsingJsoup(earliestCourse);
        verifyCourseDetailsUsingJsoup(latestCourse);
    }
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
    public void highlightEarliestAndLatestCoursesVisually() {
        WebElement earliestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(getMinStartDate()))
                .orElseThrow(() -> new NoSuchElementException("Ранний курс не найден"));

        WebElement latestCourse = findElementByPredicate(catalog,
                e -> e.getAttribute("data-start-date").equals(getMaxStartDate()))
                .orElseThrow(() -> new NoSuchElementException("Поздний курс не найден"));

        executeScript("arguments[0].style.backgroundColor = '#FFD700';", earliestCourse); // Золотистый цвет
        executeScript("arguments[0].style.backgroundColor = '#8B008B';", latestCourse);   // Фиолетовый цвет
    }
    private String getMinStartDate() {
        return catalog.stream()
                .map(e -> e.getAttribute("data-start-date"))
                .min(String::compareTo)
                .orElse(null);
    }
    private String getMaxStartDate() {
        return catalog.stream()
                .map(e -> e.getAttribute("data-start-date"))
                .max(String::compareTo)
                .orElse(null);
    }
}