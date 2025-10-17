import java.sql.*;

public class Database {
    private Connection connection;
    private Statement statement;

    // Constructor
    public Database() {
        try {
            // Sesuaikan nama database jika berbeda
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_product",
                    "root",
                    "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk SELECT query [cite: 1316]
    public ResultSet selectQuery(String sql) {
        try {
            statement.executeQuery(sql);
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk INSERT, UPDATE, DELETE query [cite: 1324]
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Getter [cite: 1332]
    public Statement getStatement() {
        return statement;
    }
}
