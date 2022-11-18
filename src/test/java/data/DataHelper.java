package data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("92c5055b-fe80-4294-b010-ff954c64b004", "vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("2ef76e4e-da41-4784-b3c3-dfb4b5e676ff", "petya", "123qwerty");
    }

    @Value
    public static class AuthInfo {
        private String id;
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
           return runner.query(conn, codeSQL, new ScalarHandler<>());
    }
}
}

