package Panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://QUANGHAI-KHMT-U\\SQLEXPRESS:1433;databaseName=flappybird_java;encrypt = true;trustServerCertificate = true";
			String userName = "sa";
			String password = "123456789";

			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();

			// String sql = "select * from table_flappybird";
			// ResultSet rs = statement.executeQuery(sql);
			// while (rs.next()) {
			// System.out.println(rs.getInt(1)); // Assuming the first column is of type INT
			// System.out.println(rs.getInt(2)); // Assuming the second column is of type
			// VARCHAR
			// } // Added the closing brace for the while loop
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
