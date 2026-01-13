package factory.settings;

import org.openqa.selenium.remote.AbstractDriverOptions;

public interface ISettings<T extends AbstractDriverOptions<?>> {

    T settings(T t);
}
