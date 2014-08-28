package jm.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.com.JmProperties;

public class Trx {
	
	private Connection conn = null;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public Connection getConn() throws SQLException, ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, IOException {
		return this.getConn(new JmProperties());
	}
	
	/**
	 * JmProperty 설정 파일을 입력해서 Connection 을 가져오는 메서드.
	 * Null 입력시 새로운 JmProperty 가져옴.
	 * @param property
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public Connection getConn(JmProperties property) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, FileNotFoundException, IOException {
		if(property == null){
			property = new JmProperties();
		}
		return this.getConn(property.get("dbType"), property.get("dbUrl"), property.get("dbDb"), property.get("dbUser"), property.get("dbPassswd"));
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
	public Connection getConn(String dbType, String url, String db, String user, String pw) throws SQLException, ClassNotFoundException {
		if(this.conn == null || this.conn.isClosed()){
			if("maria".equals(dbType.toLowerCase())){
				Class.forName("org.mariadb.jdbc.Driver");
			} else {
				Class.forName("com.mysql.jdbc.Driver");
			}
			
			this.conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+db, user, pw);
		}
		return this.conn;
	}
	
	/**
	 * 컨넥션 종료.
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		if(this.conn != null){
			this.conn.close();
		}
	}
	
}
