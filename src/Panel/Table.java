package Panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Table {

    private static final String url = "jdbc:sqlserver://QUANGHAI-KHMT-U\\SQLEXPRESS:1433;databaseName=flappybird_java;encrypt = true;trustServerCertificate = true";
    private static final String userName = "sa";
    private static final String password = "123456789";

    public static CompletableFuture<List<Integer>> getTopScoresAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Integer> topScores = new ArrayList<>();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url, userName, password);
                String sql = "SELECT DISTINCT TOP 5 Score FROM table_flappybird ORDER BY Score DESC";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int score = resultSet.getInt("Score");
                    topScores.add(score);
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return topScores;
        });
    }
}
