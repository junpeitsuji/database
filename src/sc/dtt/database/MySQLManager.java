package sc.dtt.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Junpei Tsuji
 * @version 1.0.0-2012.01.10
 */
public class MySQLManager extends DatabaseManager {

	protected String hostname = null;
	protected String dbname   = null;
	protected String username = null;
	protected String password = null;

	@Override
	protected void init(String filename) {

		Properties prop = new Properties();
		try {
			InputStream stream = new FileInputStream(filename);
			prop.loadFromXML(stream);
			stream.close();

			this.hostname = prop.getProperty("hostname");
			this.dbname   = prop.getProperty("dbname");
			this.username = prop.getProperty("username");
			this.password = prop.getProperty("password");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	protected Connection connect() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		// JDBC Driver の登録
		Class.forName("com.mysql.jdbc.Driver").newInstance();

		// データベースへの接続
		Connection con = DriverManager.getConnection("jdbc:mysql://" + hostname
				+ "/" + dbname, username, password);

		return con;
	}

}
