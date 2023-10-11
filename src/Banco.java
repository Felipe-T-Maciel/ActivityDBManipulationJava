import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    static Connection getConnection() throws SQLException {
        String data = "jdbc:mysql://localhost:3306/galaxydb";
        String user = "root";
        String senha = "root";
        return DriverManager.getConnection(data, user, senha);
    }
}
