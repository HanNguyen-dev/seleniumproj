package hellocucumber.stepdefinitions;

import hellocucumber.utils.WebDriverUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchForFood {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchForFood() {
        driver = WebDriverUtil.getWebDriver();
        wait = WebDriverUtil.getWait();
    }

    @Given("I am on the Google search page")
    public void I_visit_google() {
        driver.get("https://google.com");
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
        driver.quit();
    }

}
