package MySQLHelper;

import java.sql.*;

import com.mysql.fabric.jdbc.*;

class ConnectionFabric {
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/task";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    ConnectionFabric(){
        createConnection();
    }

    private void createConnection(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            if (connection.isClosed()){
                createConnection();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    Statement getStatement(){
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
