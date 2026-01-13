package support;

import com.google.inject.ImplementedBy;
import org.openqa.selenium.WebDriver;

@ImplementedBy(GuiceScoped.class)
public interface DriverProvider {

    WebDriver provideDriver();
}

