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
	
	public Connection getLocalConn() throws SQLException, ClassNotFoundException{
		JmProperties property = new JmProperties();
		return this.getConn("127.0.0.1", property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
	}
	
	public Connection getRemoteConn() throws SQLException, ClassNotFoundException{
		JmProperties property = new JmProperties();
		return this.getConn(property.get("dbUrl"), property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
	}
	
	public Connection getConn(String url, String db, String user, String pw) throws SQLException, ClassNotFoundException {
		if(this.conn == null || this.conn.isClosed()){
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+db, user, pw);
		}
		return this.conn;
	}
	
	public void close() throws SQLException{
		if(this.conn != null)
			this.conn.close();
	}
	
}
