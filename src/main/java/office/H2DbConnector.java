package office;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DbConnector implements DbConnector {
    private final String dbUrl;

    public H2DbConnector(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }
}
