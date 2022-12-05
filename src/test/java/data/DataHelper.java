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
    public static String getUserStatus(String login) {
        var runner = new QueryRunner();
        var statusSQL = "SELECT status from users where login= ?;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            return runner.query(conn, statusSQL, login, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getUserId1(String login) {
        var runner = new QueryRunner();
        var idSQL = "SELECT id from users where login=?;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            return runner.query(conn, idSQL, login, new ScalarHandler<>());
        }
    }


    @SneakyThrows
    public static void clearSUT() {
        var runner = new QueryRunner();
        var deleteCodesTableSQL = "DELETE FROM auth_codes WHERE user_id=?;";
        var deleteCardsInfoTableSQL = "DELETE FROM cards WHERE user_id=?;";
        var deleteUsersInfoTableSQL1 = "DELETE FROM users WHERE id=?;";
        var deleteUsersInfoTableSQL2 = "DELETE FROM users WHERE id=?;";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            var deleteCodes = runner.update(conn, deleteCodesTableSQL, getUserId1("vasya"));
            var deleteCardsInfo = runner.update(conn, deleteCardsInfoTableSQL, getUserId1("vasya"));
            var deleteUsers1 = runner.update(conn, deleteUsersInfoTableSQL1,getUserId1("vasya"));
            var deleteUsers2 = runner.update(conn, deleteUsersInfoTableSQL2,getUserId1("petya"));
        }
    }

    @SneakyThrows
    public static void clearSUT1() {
        var runner = new QueryRunner();
        var deleteCodesTableSQL = "DELETE FROM auth_codes ;";
        var deleteCardsInfoTableSQL = "DELETE FROM cards ;";
        var deleteUsersInfoTableSQL1 = "DELETE FROM users ;";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            var deleteCodes = runner.update(conn, deleteCodesTableSQL, getUserId1("vasya"));
            var deleteCardsInfo = runner.update(conn, deleteCardsInfoTableSQL, getUserId1("vasya"));
            var deleteUsers1 = runner.update(conn, deleteUsersInfoTableSQL1,getUserId1("vasya"));
        }
    }
}

