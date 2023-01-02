package surfing.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import surfing.util.DateManeger;

public class DBManager {
	private static DBManager instance;
	private Connection connection;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "javase";
	private String pass = "1234";
	
	private String accessTime;

	public DBManager() {
		connect();
	}
	
	public String getAccessTime() {
		return accessTime;
	}

	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public void connect() {
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공 : " + DateManeger.getLocalTime());

			connection = DriverManager.getConnection(url, user, pass);
			accessTime = DateManeger.getLocalTime();
			System.out.println("DB 접속 성공 : " + accessTime);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("DB 접속 종료 : " + DateManeger.getLocalTime());
	}

	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("pstmt 사용완료 : " + DateManeger.getLocalTime());
	}

	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("pstmt, rs 사용완료 : " + DateManeger.getLocalTime());
	}
}
