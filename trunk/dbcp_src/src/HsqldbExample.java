import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 测试一下hsqldb，
 * @author Administrator , 2012-10-28
 *
 */
public class HsqldbExample {
	 public static void main(String[] args) {
	        // First we set up the BasicDataSource.
	        // Normally this would be handled auto-magically by
	        // an external configuration, but in this example we'll
	        // do it manually.
	        //
		 String connectURI = "jdbc:hsqldb:hsql://localhost/";
		 String testSql = "select * from student ";
		 
	        System.out.println("Setting up data source.");
	        DataSource dataSource = setupDataSource(connectURI);
	        System.out.println("Done.");
	        

	        //
	        // Now, we can use JDBC DataSource as we normally would.
	        //
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rset = null;

	        try {
	            System.out.println("Creating connection.");
	            conn = dataSource.getConnection();
	            System.out.println("Creating statement.");
	            stmt = conn.createStatement();
	            System.out.println("Executing statement.");
	            rset = stmt.executeQuery(testSql);
	            System.out.println("Results:");
	            int numcols = rset.getMetaData().getColumnCount();
	            while(rset.next()) {
	                for(int i=1;i<=numcols;i++) {
	                    System.out.print("\t" + rset.getString(i));
	                }
	                System.out.println("");
	            }
	        } catch(SQLException e) {
	            e.printStackTrace();
	        } finally {
	        	printDataSourceStats(dataSource);
	            try { if (rset != null) rset.close(); } catch(Exception e) { }
	            try { if (stmt != null) stmt.close(); } catch(Exception e) { }
	            try { if (conn != null) conn.close(); } catch(Exception e) { }
	        }
	    }

	    public static DataSource setupDataSource(String connectURI) {
	        BasicDataSource ds = new BasicDataSource();
	        ds.setDriverClassName("org.hsqldb.jdbcDriver");
	        ds.setUsername("SA");
	        ds.setPassword("");
	        ds.setUrl(connectURI);
	        return ds;
	    }

	    public static void printDataSourceStats(DataSource ds) {
	        BasicDataSource bds = (BasicDataSource) ds;
	        System.out.println("NumActive: " + bds.getNumActive());
	        System.out.println("NumIdle: " + bds.getNumIdle());
	    }

	    public static void shutdownDataSource(DataSource ds) throws SQLException {
	        BasicDataSource bds = (BasicDataSource) ds;
	        bds.close();
	    }
}
