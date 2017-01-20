package MySQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDAO {
    private ConnectionFabric connection = new ConnectionFabric();

    public void execute(String query){
        try {
            connection.getStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String query){
        ResultSet resultSet = null;
        try {
            resultSet = connection.getStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
