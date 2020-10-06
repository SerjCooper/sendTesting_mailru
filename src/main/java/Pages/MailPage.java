package Pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MailPage extends BasePage {

    private final By mailItemsXpath = By.xpath("//span[contains(@class, 'llc__subject')]");
    private final By writeMailXpath = By.xpath("//a[contains(@class, 'compose-button')]");
    private final By mailTextBoxXpath = By.xpath("//div[contains(@class, 'cke_editable')]/div[1]");
    private final By sendMailXpath = By.xpath("//span[contains(@title, 'Отправить')]");
    private final By contactToXpath = By.xpath("//div[contains(@data-type, 'to')]//input");
    private final By subjectMailXpath = By.xpath("//input[contains(@name, 'Subject')]");
    private final By successMessageXpath = By.linkText("Письмо отправлено");
    private final By mailBodyXpath = By.xpath("//div[contains(@class, 'letter-body__body-content')]");
    private final By searchPanelXpath = By.xpath("//div[contains(@class, 'search-panel-button')]");

    public MailPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открытие окна создания письма")
    public MailPage openComposeWindow(){
        driver.findElement(writeMailXpath).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(mailTextBoxXpath));
        return this;
    }

    @Step("Отправка сообщения")
    public MailPage sendMailClick(){
        driver.findElement(sendMailXpath).click();
        return this;
    }

    @Step("Очистка и ввод текста сообщения")
    public MailPage writeMessage(String message){
        driver.findElement(mailTextBoxXpath).sendKeys(Keys.chord(Keys.CONTROL, "A") + message);
        return this;
    }

    @Step("Указание получателя сообщения")
    public MailPage setContactTo(String contact){
        driver.findElement(contactToXpath).sendKeys(contact);
        return this;
    }

    @Step("Указание темы письма")
    public MailPage setMailSubject(String subject){
        driver.findElement(subjectMailXpath).sendKeys(subject);
        return this;
    }

    public MailPage sendMail(String contact, String subject, String message){
        wait.until(ExpectedConditions.elementToBeClickable(writeMailXpath));
        openComposeWindow();
        setContactTo(contact);
        setMailSubject(subject);
        writeMessage(message);
        sendMailClick();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageXpath));
        return this;
    }

    @Step("Проверка получения сообщения")
    public MailPage verifyMessage(String subject, String message) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchPanelXpath));
        actions
                .click(driver.findElement(searchPanelXpath))
                .sendKeys(subject + Keys.ENTER)
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(mailItemsXpath));
        driver.findElement(mailItemsXpath).click();
        Assert.assertTrue(driver
                .findElement(mailBodyXpath)
                .findElement(By.xpath("//div[contains(text(), '" + message +"')]"))
                .isDisplayed(), "Текст полученного письма не совпадает с отправленным");
        return this;
    }
}
