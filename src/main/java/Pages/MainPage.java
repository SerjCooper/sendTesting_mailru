package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MainPage extends BasePage {

    private final By loginFieldXpath = By.xpath("//input[contains(@name, 'login')]");
    private final By passBtnXpath = By.xpath("//input[contains(@class, 'o-control')]");
    private final By passFieldXpath = By.xpath("//input[contains(@name, 'password')]");
    private final By enterBtnXpath = By.xpath("//button[contains(@id, 'mailbox:submit-button')]");

    private final String mailPageUrl = "https://e.mail.ru/inbox/";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизация в e-mail")
    public MailPage loginMail(String login, String pass){
        driver.findElement(loginFieldXpath).sendKeys(login);
        driver.findElement(passBtnXpath).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passFieldXpath));
        driver.findElement(passFieldXpath).sendKeys(pass);
        driver.findElement(enterBtnXpath).click();
        wait.until(ExpectedConditions.urlContains(mailPageUrl));
        Assert.assertTrue(driver.getCurrentUrl().contains(mailPageUrl));
        return new MailPage(driver);
    }
}
