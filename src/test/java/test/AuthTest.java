package test;

import com.github.javafaker.Faker;
import data.DataHelper;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;


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
}
