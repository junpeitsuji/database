package sc.dtt.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import sc.dtt.database.DatabaseManager;

// DatabaseManagerクラスをインポートする

/**
 * データベースのチェックのためのクラス
 * mysql-connector-java-5.1.17-bin.jar
 * をビルドパスに追加して使うこと
 * @author Junpei Tsuji
 * @version 1.0.0-2012.01.10
 */
public class DatabaseTest {

	/**
	 * メインメソッド
	 * @param args
	 */
	public static void main(String[] args){

		try {
			/*
			 * データを問い合わせて取得（例）
			 */
			// データベースのインスタンスを取得
			DatabaseManager instance = DatabaseManager.getInstance("MySQL", "db.xml");

			// 問い合わせのSQLを作成
			String sql = "select id, name from users;";
			System.out.println(sql);

			// SQLを発行し結果を取得
			ResultSet res = instance.executeQuery(sql);

			// 問合せ結果の表示
			while ( res.next() ) {
				// 列番号による指定
				System.out.println(res.getInt("id") + "\t" + res.getString("name"));
			}

			// 結果セットをクローズ
			res.close();


			/*
			 * データを追加（例）
			 */
			// データベースのインスタンスを取得
			instance = DatabaseManager.getInstance();

			// SQLを作成
			sql = "INSERT INTO users (name) VALUES ('testperson');";
			System.out.println(sql);

			// SQLを実行得
			instance.executeUpdate(sql);
			// 結果セットをクローズ
			res.close();

			/*
			 * データを問い合わせて取得（例）
			 */
			// データベースのインスタンスを取得
			instance = DatabaseManager.getInstance("MySQL", "db.xml");

			// 問い合わせのSQLを作成
			sql = "select id, name from users;";
			System.out.println(sql);

			// SQLを発行し結果を取得
			res = instance.executeQuery(sql);

			// 問合せ結果の表示
			while ( res.next() ) {
				// 列番号による指定
				System.out.println(res.getInt("id") + "\t" + res.getString("name"));
			}

			// 結果セットをクローズ
			res.close();


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
