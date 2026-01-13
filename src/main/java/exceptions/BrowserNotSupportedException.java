package exceptions;

public class BrowserNotSupportedException extends RuntimeException {

    public BrowserNotSupportedException(String browserType) {
        super(String.format("Browser %s not supported", browserType));
    }
}
