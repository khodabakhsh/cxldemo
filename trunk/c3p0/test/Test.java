import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Test {
	private static ComboPooledDataSource ds = new ComboPooledDataSource();

	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws SQLException {
		Connection con = getConnection();
		Statement statement = con.createStatement();
		statement.execute("select * from test");
		ResultSet rs = statement.getResultSet();
		while (rs.next()) {
			System.out.println(rs.getString("id") + " : " + rs.getString("name"));
		}
		rs.close();
		statement.close();
		con.close();
	}
}
