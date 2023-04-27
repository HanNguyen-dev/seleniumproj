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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private EnvironmentUtil environmentUtil;

    @Given("I am on the search page")
    public void I_visit_search_page() {
        initiate();
        driver.get(environmentUtil.getSearchUrl());
    }

    @When("I search for {string}")
    public void I_search_for_word(String query) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @When("I enter the following search terms")
    public void I_enter_the_following_search_term(Map<String, String> searchTerms) {
        for (String term : searchTerms.values()) {
            I_search_for_word(term);
        }
    }

    @Then("the page should start with {string}")
    public void check_title(String title) {
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return webDriver.getTitle().toLowerCase().startsWith(title);
            }
        });
    }
    @Then("succeed")
    public void succeed() {
        assertTrue(true);
    }


    @After()
    public void onAfter (Scenario scenario) {
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
//        driver.quit();
    }

    public void initiate() {
        driver = WebDriverUtil.getWebDriver();
        wait = WebDriverUtil.getWait();
        environmentUtil = EnvironmentUtil.getInstance();
    }

}
