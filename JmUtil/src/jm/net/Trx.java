package jm.net;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.com.JmProperties;

public class Trx {

	private static Trx instance = null;
	private Connection conn = null;
	
	private Trx(){};
	
	public static Trx getInstance() {
		if(instance == null){
			instance = new Trx();
		}
		return instance;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConn() throws SQLException, ClassNotFoundException {
		return this.getConn(new JmProperties());
	}
	
	/**
	 * JmProperty 설정 파일을 입력해서 Connection 을 가져오는 메서드.
	 * Null 입력시 새로운 JmProperty 가져옴.
	 * @param property
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConn(JmProperties property) throws SQLException, ClassNotFoundException {
		if(property == null){
			property = new JmProperties();
		}
		return this.getConn(property.get("dbUrl"), property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
	}
	
	/**
	 * 로컬 connection
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getLocalConn() throws SQLException, ClassNotFoundException{
		JmProperties property = new JmProperties();
		return this.getConn("127.0.0.1", property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
	}
	
	/**
	 * 리모트 connection
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getRemoteConn() throws SQLException, ClassNotFoundException{
		JmProperties property = new JmProperties();
		return this.getConn(property.get("dbUrl"), property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
	}
	
	/**
	 * 컨넥션 가져오는 메서드.
	 * @param url
	 * @param db
	 * @param user
	 * @param pw
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConn(String url, String db, String user, String pw) throws SQLException, ClassNotFoundException {
		if(this.conn == null || this.conn.isClosed()){
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+db, user, pw);
		}
		return this.conn;
	}
	
	/**
	 * 컨넥션 종료.
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		if(this.conn != null)
			this.conn.close();
	}
	
}
