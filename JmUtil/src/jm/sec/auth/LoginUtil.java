package jm.sec.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import jm.com.JmProperties;
import jm.net.Dao;
import jm.net.Trx;

public class LoginUtil {

	public void login(){
		
	}
	
	public void logout(){
		
	}
	
	public boolean checkId(String id, String passwd) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		int resCnt = 0;
		
		Dao dao = Dao.getInstance();
		Trx trx = new Trx();
		Connection conn = null;
		StringBuffer sql = new StringBuffer();
		
		JmProperties property = new JmProperties();
		
		try {
			conn = trx.getConn();
			
			sql.append("select count(*) from ");
			sql.append(property.get("loginTable"));
			sql.append(" where ");
			sql.append(property.get("loginId") + "=? ");
			sql.append(" and ");
			sql.append(property.get("loginPasswd") + "=? ");
			
			System.out.println(sql.toString());
			String[] params = {id, passwd};
			
			resCnt = dao.getCount(conn, sql.toString(), params);
			
			System.out.println("resCnt: "+resCnt);
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(resCnt == 0){
			return false;
		} else {
			return true;
		}
		
	}
}
