package components;

import annotations.Component;
import com.google.inject.Inject;
import commons.AbsCommon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import support.GuiceScoped;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public abstract class AbsComponent extends AbsCommon {

    {

        assertThat(waiter
                .waitForCondition(ExpectedConditions.visibilityOfAllElementsLocatedBy(Objects.requireNonNull
                        (getByComponent(), "Locator is required for visibility check"))))
                .as("Error")
                .isTrue();
    }

    @Inject
    public AbsComponent(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    private By getByComponent() {
        Class<?> clazz = getClass();

        if (clazz.isAnnotationPresent(Component.class)) {
            Component component = clazz.getAnnotation(Component.class);
            String[] componentVal = component.value().split(":");

            switch (componentVal[0].trim()) {
                case "css":
                    return By.cssSelector(componentVal[1]);
                case "xpath":
                    return By.xpath(componentVal[1]);
            }
        }
        return null;
    }

    public WebElement getComponentEntry() {
        return $(getByComponent());
    }
}
