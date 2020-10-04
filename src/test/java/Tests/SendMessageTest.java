package Tests;

import Pages.MailPage;
import Pages.MainPage;
import io.qameta.allure.Feature;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SendMessageTest extends BaseTest{

    private String url = "https://mail.ru";

    @Feature("Проверка получения сообщения")
    @Test
    @Parameters({"login_1", "login_2", "pass_1", "pass_2"})
    public void mailReceivingTest(String login_1, String login_2, String pass_1, String pass_2){
        driver.get(url);
        //В качестве уникального ключа для поиска письма используем поле "Тема"
        String subject = "test_" + LocalDateTime.now();
        String message = "Я скажу то, что для тебя не новость. Мир не такой уж солнечный и приветливый. Э" +
                "то очень опасное, жёсткое место. И если только дашь слабину, он опрокинет с такой силой тебя, " +
                "что больше уже не встанешь. Ни ты, ни я, никто на свете не бьёт так сильно, как жизнь. Совсем не " +
                "важно, как ты ударишь, а важно, КАКОЙ ДЕРЖИШЬ УДАР, как двигаешься вперёд. Будешь идти — ИДИ, если с" +
                " испугу не свернёшь. Только так побеждают! Если знаешь, чего ты стОишь, иди и бери своё, но будь готов " +
                "удары держать, а не плакаться и говорить: «Я ничего не добился из-за него, из-за неё, из-за кого-то»! " +
                "Так делают трУсы, а ты не трус! Быть этого не может! …я всегда буду тебя любить, что бы ни случилось. " +
                "Ты мой сын — плоть от плоти, самое дорогое, что у меня есть. Но пока ты не поверишь в себя, жизни не будет.";

        //Авторизуемся в первом ящике и отправляем сообщение на второй
        MainPage mainPage = new MainPage(driver);
        MailPage mailPage = mainPage.loginMail(login_1, pass_1);
        mailPage.sendMail(login_2 + "@mail.ru", subject, message);

        //Чистим куки и создаём девственную вкладку
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("window.open(\""+ url +"\")");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        //Авторизуемся во втором ящике и ищем письмо отправленное первым. Ищем по теме письма
        mainPage.loginMail(login_2, pass_2);
        mailPage.verifyMessage(subject, message);

        driver.close();
        driver.switchTo().window(tabs.get(0));
    }
}
