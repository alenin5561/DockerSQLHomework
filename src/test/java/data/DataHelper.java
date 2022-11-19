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

    public static AuthInfo getAuthInfo() { return new AuthInfo( "vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidInfo() {
        return new AuthInfo( "vasya", "qwerty321");
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

    public static void deleteFirstUserFromSUT() throws SQLException {
        var runner = new QueryRunner();
        var deleteCodesTableSQL = "DELETE FROM auth_codes WHERE login =?;";
        var deleteCardsTransTableSQL = "DELETE FROM card_transactions WHERE login =?;";
        var deleteCardsInfoTableSQL = "DELETE FROM cards WHERE login =?;";
        var deleteUsersInfoTableSQL = "DELETE FROM users WHERE login =?;";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "user", "pass");
        ) {
            var deleteCodes1 = runner.query(conn, deleteCodesTableSQL, ); //как сделать гет на логин?
            var deleteCardsTrans1 = runner.query(conn, deleteCardsTransTableSQL, );
            var deleteCardsInfo1 = runner.query(conn, deleteCardsInfoTableSQL, );
            var deleteUsers1 = runner.query(conn, deleteUsersInfoTableSQL, );
        }
    }
}

