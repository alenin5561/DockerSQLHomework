package data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidInfo() {
        return new AuthInfo("vasya", "qwerty321");
    }


    @Value
    public static class AuthInfo {
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
        var codeSQL = "SELECT code from auth_codes order by created DESC LIMIT 1";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            return runner.query(conn, codeSQL, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getUserStatus() {
        var runner = new QueryRunner();
        var statusSQL = "SELECT status from users where login= 'vasya';";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            return runner.query(conn, statusSQL, new ScalarHandler<>());
        }
    }


    //реализовать метод!
    public static void clearSUT() throws SQLException {
        var runner = new QueryRunner();
        var deleteCodesTableSQL = "DELETE FROM auth_codes WHERE user_id=?;";
        var deleteCardsInfoTableSQL = "DELETE FROM cards WHERE user_id=?;";
        var deleteUsersInfoTableSQL1 = "DELETE FROM users WHERE id=?;";
        var deleteUsersInfoTableSQL2 = "DELETE FROM users WHERE id=?;";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            var deleteCodes = runner.update(conn, deleteCodesTableSQL, "966490ae-1d96-44b2-ae22-c33fb4344c2b");
            var deleteCardsInfo = runner.update(conn, deleteCardsInfoTableSQL, "966490ae-1d96-44b2-ae22-c33fb4344c2b");
            var deleteUsers1 = runner.update(conn, deleteUsersInfoTableSQL1, "966490ae-1d96-44b2-ae22-c33fb4344c2b");
            var deleteUsers2 = runner.update(conn, deleteUsersInfoTableSQL2, "b3115e67-6ef9-4258-beac-52d82014f7db");
        }
    }
}

