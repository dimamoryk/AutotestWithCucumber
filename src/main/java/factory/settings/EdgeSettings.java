package factory.settings;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class EdgeSettings implements ISettings<EdgeOptions> {

    @Override
    public EdgeOptions settings(EdgeOptions EdgeOptions) {
        EdgeOptions.addArguments("--start-fullscreen");

        return EdgeOptions;
    }
}
