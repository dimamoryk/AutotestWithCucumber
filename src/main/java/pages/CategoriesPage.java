package pages;

import annotations.UrlTemplate;
import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import support.GuiceScoped;

import java.util.List;


@UrlTemplate(value = "/courses?categories=programming")
public class CategoriesPage extends AbsBasePage<CategoriesPage> {


    @FindBy(css = "zzdkm7")
    protected List<WebElement> courses;

    @Inject
    public CategoriesPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public WebElement findCourseByName(String courseName) {
        return courses.stream()
                .filter(course -> course.getText().contains(courseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found."));
    }

    public CategoriesPage clickSelectedCourseByName(String courseName) {
        WebElement selectedCourseTile = findCourseByName(courseName);
        selectedCourseTile.click();

        return this;
    }
}