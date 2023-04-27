package hellocucumber.stepdefinitions.seleniumexample;

import hellocucumber.utils.EnvironmentUtil;
import hellocucumber.utils.WebDriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SeleniumSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private EnvironmentUtil environmentUtil;

    private XSSFWorkbook workbook;

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

    @When("I load the excel file from {string}")
    public void I_load_the_excel_file_from(String excelFilePath) {
        try {
            File file = new File(excelFilePath);
            FileInputStream inputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println("Loading excel failed");
            fail();
        }
    }

    @When("Search term from loaded excel file")
    public void search_term_from_excel_file() {
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        XSSFRow row2 = sheet.getRow(1);

        String firstSearchTerm = row2.getCell(0).toString();
        String secondSearchTerm = row2.getCell(1).toString();

        System.out.println("First search term from excel: " + firstSearchTerm);
        System.out.println("Second search term from excel: " + secondSearchTerm);

        I_search_for_word(firstSearchTerm);
        I_search_for_word(secondSearchTerm);
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
