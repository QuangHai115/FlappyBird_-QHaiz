package Panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class Data {
	private static final String url = "jdbc:sqlserver://QUANGHAI-KHMT-U\\SQLEXPRESS:1433;databaseName=flappybird_java;encrypt = true;trustServerCertificate = true";
	private static final String userName = "sa";
	private static final String password = "123456789";

	public static CompletableFuture<Void> saveScoreAsync(int score) {
		return CompletableFuture.runAsync(() -> {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				connection = DriverManager.getConnection(url, userName, password);
				String sql = "INSERT INTO table_flappybird (Score) VALUES (?)";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, score);

				// Thực thi truy vấn
				preparedStatement.executeUpdate();

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} finally {
				try {
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
		});
	}
}
