package jm.net;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;

public class Dao {
	public static Dao instance = null;
	
	private Dao(){}
	
	public static Dao getInstance(){
		if(instance == null){
			instance = new Dao();
		}
		return instance;
	}
	
	/**
	 * count(*) 쿼리 결과를 int 로 반환하는 메서드.
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public int getCount( Connection conn, String sql, String[] params ){
		int res = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					pstmt.setString((i+1), params[i]);
				}
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				res = rs.getInt(0);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				System.out.println(se.toString());
			}
		}
		return res;
	}
	
	/**
	 * count(*) 쿼리 결과를 long 으로 반환하는 메서드.
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public long getLongCount( Connection conn, String sql, String[] params ){
		long res = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					pstmt.setString((i+1), params[i]);
				}
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				res = rs.getLong(0);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				System.out.println(se.toString());
			}
		}
		return res;
	}
	
	
	public DbEntity[] getResult( Connection conn, String sql, String[] params ){
		DbEntity[] res = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					pstmt.setString((i+1), params[i]);
				}
			}
			rs = pstmt.executeQuery();
			meta = rs.getMetaData();
			res = new DbEntity[meta.getColumnCount()];
			int v = 0;
			if (rs.next()) {
				for(int j=0; j < meta.getColumnCount(); j++){
					res[v] = new DbEntity();
					res[v].put(meta.getCatalogName(j), rs.getString(j));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				System.out.println(se.toString());
			}
		}
		
		return res;
	}
	
	
}
