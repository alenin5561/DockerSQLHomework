package test;

import data.DataHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.clearSUT;
import static data.DataHelper.clearSUT1;


public class AuthTest {

    @Test
    void positiveTest() {
        open("http://localhost:7777");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify((verificationCode));
    }

    @Test
    void invalidPasswordThreeTimes() {
        open("http://localhost:7777");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidInfo();
        var verificationPage = loginPage.threeTimesInvalidPassword(authInfo);
        var actual = DataHelper.getUserStatus("vasya");
        var expected = "blocked";
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    public static void tearDown(){
        clearSUT1();
    }

    //для запуска SUT java -jar ./artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/database -P:jdbc.user=user -P:jdbc.password=pass
}
