package hellocucumber.stepdefinitions.seleniumexample;

import hellocucumber.utils.EnvironmentUtil;
import hellocucumber.utils.WebDriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumSteps {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final EnvironmentUtil environmentUtil;

    public SeleniumSteps() {
        driver = WebDriverUtil.getWebDriver();
        wait = WebDriverUtil.getWait();
        environmentUtil = EnvironmentUtil.getInstance();
    }

    @Given("I am on the search page")
    public void I_visit_search_page() {
        driver.get(environmentUtil.getSearchUrl());
    }

    @When("I search for {string}")
    public void I_search_for_word(String query) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("the page should start with {string}")
    public void check_title(String title) {
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return webDriver.getTitle().toLowerCase().startsWith(title);
            }
        });
    }

    @After()
    public void onAfter (Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
        driver.close();
    }

}
