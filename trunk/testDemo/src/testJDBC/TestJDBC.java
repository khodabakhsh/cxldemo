package testJDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 一个jdbc例子
 * @author caixl
 *
 */
public class TestJDBC {
	public static Connection getConnectionByJDBC() {
		Connection conn = null;
		try { // 装载驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("装载驱动异常！");
			e.printStackTrace();
		}
		try { // 建立JDBC连接
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test_development", "root",
					"password");
		} catch (SQLException e) {
			System.out.println("链接数据库异常！");
			e.printStackTrace();
		}
		return conn;
	}

	public static void test() {
		Connection conn = getConnectionByJDBC();
		String sqlx = "select t.id,t.name,t.sex  from cxls t order by t.sex asc";
		try { // 创建一个JDBC声明
			Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			// 执行查询
			ResultSet rs = stmt.executeQuery(sqlx);
			while (rs.next()) {
				String sex = rs.getString("sex"); // sex 为查询语句返回的列,在此为sqlx语句返回的列名
				String name = rs.getString("name");
				int id = rs.getInt("id");
				System.out.println("id:" + id + "   性别:" + sex + "    姓名:" + name);
			}
			//insert ,update ,delete用executeUpdate方法
			int insertNum = stmt.executeUpdate("insert into cxls(sex,name) values('3','4')");//insert ,update ,delete
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			// 预防性关闭连接(避免异常发生时在try语句块关闭连接没有执行)
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	// 测试代码
	public static void main(String args[]) {
		TestJDBC.test();
	}
}

//JDBC的陷阱
//
//1、conn一定要在finally语句块进行关闭。
//
//2、Statement、ResultSet尽可能缩小其变量作用域。
//
//3、Statement可以使用多次，定义多个。一个Statement对象只和一个ResultSet对象关联，并且是最后一次查询。
//
//4、ResultSet在Connection、ResultSet关闭后自动关闭。


