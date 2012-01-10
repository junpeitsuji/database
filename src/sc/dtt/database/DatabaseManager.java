package sc.dtt.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * シングルトンパターンを使って，データベースのインスタンスを唯一持つデータベースコネクション管理クラス．
 * 使用するときは，getInstance()メソッドよりインスタンスを取得してアクセスする．
 * @author Junpei Tsuji
 * @version 1.0.0-2012.01.10
 */
abstract public class DatabaseManager {

	/**
	 * インスタンスを保持する変数
	 */
	private static DatabaseManager instance = null;

	/**
	 * データベースのコネクション
	 */
	private Connection connection = null;
	
	/**
	 * 使用Databaseの名称
	 */
	private static String defaultDbTypeName = "MySQL";

	/**
	 * 設定用 XMLファイルの名前
	 */
	private static String defaultFilename   = "db.xml";
	
	
	/**
	 * データベースインスタンスを返すためのメソッド．
	 * データを取得するSQLを発行するたびに使用する．
	 * 取得先のデータベースは，前回引数付きの getInstance() で設定したデータベースとなる．
	 * 
	 * @return instance - データベースのインスタンス
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public synchronized static final DatabaseManager getInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		if( DatabaseManager.instance == null ){
			return DatabaseManager.getInstance(DatabaseManager.defaultDbTypeName, DatabaseManager.defaultFilename);
		}
		else{
			return DatabaseManager.instance;
		}

	}
	
	
	/**
	 * データベースインスタンスを返すためのメソッド．
	 * データを取得するSQLを発行するたびに使用する．
	 * 引数で指定したファイル名で必ずインスタンスを生成する．
	 * 
	 * @param dbTypeName データベースのタイプ (MySQL, ..., etc.)．
	 * @param filename データベースの設定ファイル名. 
	 * @return instance - データベースのインスタンス
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public synchronized static final DatabaseManager getInstance(String dbTypeName, String filename) throws InstantiationException, IllegalAccessException, ClassNotFoundException{

		DatabaseManager.defaultDbTypeName = dbTypeName;
		DatabaseManager.defaultFilename   = filename;
		
		// [dbName] + Manager のインスタンスを生成
		String clsName = (DatabaseManager.class.getPackage().getName()) + "." + dbTypeName + "Manager";
		Object object  = Class.forName(clsName).newInstance();

		if( DatabaseManager.class.isAssignableFrom(object.getClass()) ){
			// CarFactory 継承クラスに限定する
			DatabaseManager instance = (DatabaseManager)object;
			instance.init(filename);
			return instance;
		}
		else{
			IllegalAccessException e = new IllegalAccessException();
			throw e;
		}
	}
	

	/**
	 * 引数の filename で指定するファイルを使ってデータベースの初期化を行うメソッド (サブクラスで実装する)．
	 * @param filename ファイル名
	 */
	abstract protected void init(String filename);
	
	
	/**
	 * DB サーバへ接続してコネクションを取得するメソッド (サブクラスで実装する)
	 * 
	 * @return connection - データベースのコネクション
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	abstract protected Connection connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException ;


	/**
	 * データベースにクエリを送信するメソッド
	 *
	 * @param  sql 送信するSQL文
	 * @return response - クエリ結果
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		// 接続
		if( this.connection == null ){
			this.connection = connect();
		}
		// statementの生成
		Statement stmt = this.connection.createStatement();
		// クエリの発行
		ResultSet response = stmt.executeQuery(sql);

		return response;

	}
	

	/**
	 * データベースにインサートクエリを送信するメソッド
	 *
	 * @param  sql 送信するSQL文
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public void executeUpdate(String sql) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		// 接続
		if( this.connection == null ){
			this.connection = connect();
		}
		// statementの生成
		Statement stmt = this.connection.createStatement();
		// クエリの発行
		stmt.executeUpdate(sql);
	}

}
