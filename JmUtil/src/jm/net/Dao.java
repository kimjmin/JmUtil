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
	
	/**
	 * 쿼리 결과를 DataEntity 의 배열로 반환하는 메서드.
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public DataEntity[] getResult( Connection conn, String sql, String[] params ){
		DataEntity[] res = null;
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
			res = new DataEntity[meta.getColumnCount()];
			int v = 0;
			if (rs.next()) {
				for(int j=0; j < meta.getColumnCount(); j++){
					res[v] = new DataEntity();
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
	
	/**
	 * 테이블명, 데이터셋으로 해당 데이터를 입력받는 메서드.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int inertDb(String tableName, DataEntity dataEntity){
		int result = 0;
		
		Trx trx = Trx.getInstance();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		
		String[] colums = dataEntity.keySet().toArray(new String[0]);
		int columSize = colums.length;
		
		try {
//			conn = trx.getLocalConn();
			conn = trx.getRemoteConn();
			
			sql.append("INSERT INTO ");
			sql.append(tableName + " ");
			sql.append(" ( ");
			for(int col=0; col < columSize; col++){
				sql.append(colums[col]);
				if(col < columSize-1){ sql.append(", "); }
			}
			sql.append(" ) ");
			
			sql.append("VALUES ( ");
			for(int val=0; val < columSize; val++){
				sql.append("?");
				if(val < columSize-1){ sql.append(", "); }
			}
			sql.append(" ) ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int stm=1; stm <= columSize; stm++){
				pstmt.setString(stm, dataEntity.get(colums[stm]));
			}
			
			result = pstmt.executeUpdate();

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			System.out.println(sqe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (trx != null)
					trx.close();
			} catch (SQLException se) {
				System.out.println(se.toString());
			}
		}
		return result;
	}
	
}
