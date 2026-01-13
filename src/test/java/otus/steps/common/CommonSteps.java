package otus.steps.common;

import com.google.inject.Inject;
import io.cucumber.java.ru.Дано;
import support.GuiceScoped;

public class CommonSteps {

    @Inject
    protected GuiceScoped guiceScoped;

    protected String baseUrl = System.getProperty("base.url", "https://otus.ru");

    @Дано("Открыть браузер edge")
    public void openBrowser() {
        guiceScoped.getDriver().get(baseUrl);
    }
}
