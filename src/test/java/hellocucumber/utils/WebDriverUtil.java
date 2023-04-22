package hellocucumber.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverUtil {

    private static WebDriver driver;

    private static WebDriverWait wait;

    private static final Duration TIME_OUT_SEC = Duration.ofSeconds(20);

    public static WebDriver getWebDriver() {
        if (driver == null) {
            ChromeOptions options = buildChromeOptions();
            driver = new ChromeDriver(options);
        }
        return driver;
    }
    public static WebDriverWait getWait() {
        if (wait == null) {
            if (driver == null) {
                getWebDriver();
            }
            wait = new WebDriverWait(driver, TIME_OUT_SEC);
        }
        return wait;
    }

    private static ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");
        return options;
    }

}
