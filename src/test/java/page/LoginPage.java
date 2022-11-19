package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void  clearPage() {
        loginField.doubleClick();
        loginField.sendKeys(Keys.DELETE);
        passwordField.doubleClick();
        passwordField.sendKeys(Keys.DELETE);
    }

    public LoginPage threeTimesInvalidPassword(DataHelper.AuthInfo info) {
        for (int times = 0; times <3 ; times++) {
            loginField.setValue(info.getLogin());
            passwordField.setValue(info.getPassword());
            loginButton.click();
            clearPage();
        }
        loginButton.shouldBe(Condition.disabled);
        return new LoginPage();
    }
}

