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
				res = rs.getInt(1);
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
				res = rs.getLong(1);
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
	 * 원격 DB의 쿼리 결과를 DataEntity 의 배열로 반환하는 메서드.
	 * @param sql
	 * @param params
	 * @return
	 */
	public DataEntity[] getRemoteResult(String sql, String[] params){
		DataEntity[] result = null;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getRemoteConn();
			result = getResult(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 원격 DB의 쿼리 결과를 DataEntity 의 배열로 반환하는 메서드.
	 * @param sql
	 * @param params
	 * @return
	 */
	public DataEntity[] getLocalResult(String sql, String[] params){
		DataEntity[] result = null;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getLocalConn();
			result = getResult(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
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
	 * 원격 DB에 저장.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int inertRemoteData(String tableName, DataEntity dataEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getRemoteConn();
			result = inertData(conn, tableName, dataEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 로컬 DB에 저장.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int inertLocalData(String tableName, DataEntity dataEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getLocalConn();
			result = inertData(conn, tableName, dataEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 테이블 저장을 위한 공통 메서드
	 * 테이블명, 데이터셋으로 해당 데이터를 추가하는 메서드.
	 * @param tableName : 테이블명
	 * @param dataEntity : 업데이트 할 데이터셋.
	 * @return
	 * @throws Exception 
	 */
	public int inertData(Connection conn, String tableName, DataEntity dataEntity){
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		
		String[] colums = dataEntity.keySet().toArray(new String[0]);
		int columSize = colums.length;
		
		StringBuffer values = new StringBuffer();
		
		try {
			
			sql.append("INSERT INTO \n");
			sql.append(tableName + " ");
			sql.append(" ( ");
			for(int col=0; col < columSize; col++){
				sql.append(colums[col]);
				if(col < columSize-1){ sql.append(", "); }
			}
			sql.append(") \n");
			
			sql.append("VALUES ( ");
			for(int val=0; val < columSize; val++){
				sql.append("?");
				if(val < columSize-1){ sql.append(", "); }
			}
			sql.append(" ) ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int stm=0; stm < columSize; stm++){
				pstmt.setString((stm+1), dataEntity.get(colums[stm]));
				
				//오류 출력을 위한 value 저장
				values.append(dataEntity.get(colums[stm]));
				if(stm < columSize-1){ values.append(", "); }
			}
			
			result = pstmt.executeUpdate();

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			System.out.println("SQL : " + sql.toString());
			System.out.println("Values : " + values.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 원격 DB 업데이트.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int updateRemoteData(String tableName, DataEntity dataEntity, DataEntity whereEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getRemoteConn();
			result = updateData(conn, tableName, dataEntity, whereEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 로컬 DB 업데이트.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int updateLocalData(String tableName, DataEntity dataEntity, DataEntity whereEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getLocalConn();
			result = updateData(conn, tableName, dataEntity, whereEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 테이블 업데이트를 위한 공통 메서드
	 * @param tableName : 업데이트 할 테이블명
	 * @param setEntity : 변경 할 데이터 정보 엔티티
	 * @param whereEntity : 업데이트 할 대상 검색 조건 엔티티.
	 * @return
	 * @throws Exception
	 */
	public int updateData(Connection conn, String tableName, DataEntity setEntity, DataEntity whereEntity){
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		
		String[] setColums = setEntity.keySet().toArray(new String[0]);
		int setColumSize = setColums.length;
		
		String[] whereColums = whereEntity.keySet().toArray(new String[0]);
		int whereColumSize = whereColums.length;
		
		StringBuffer values = new StringBuffer();
		
		try {
			
			sql.append("UPDATE "+ tableName + " SET \n");
			
			for(int col=0; col < setColumSize; col++){
				sql.append(setColums[col]+"=? ");
				if(col < setColumSize-1){ sql.append(", \n"); }
			}
			
			sql.append("\nWHERE \n");
			
			for(int val=0; val < whereColumSize; val++){
				sql.append(whereColums[val]+"=?");
				if(val < whereColumSize-1){ sql.append("AND \n"); }
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			int totalSize = 1;
			for(int stm=0; stm < setColumSize; stm++){
				pstmt.setString((totalSize), setEntity.get(setColums[stm]));
				//오류 출력을 위한 value 저장
				values.append(setEntity.get(setColums[stm]));
				if(stm < setColumSize-1){ values.append(", "); } else { values.append("\n"); }
				totalSize++;
			}
			
			for(int stm=0; stm < whereColumSize; stm++){
				pstmt.setString((totalSize), whereEntity.get(whereColums[stm]));
				//오류 출력을 위한 value 저장
				values.append(whereEntity.get(whereColums[stm]));
				if(stm < whereColumSize-1){ values.append(", "); }
				totalSize++;
			}
			
			result = pstmt.executeUpdate();

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			System.out.println("SQL : " + sql.toString());
			System.out.println("Values : " + values.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	 * 원격 DB 삭제.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int deleteRemoteData(String tableName, DataEntity whereEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getRemoteConn();
			result = deleteData(conn, tableName, whereEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 로컬 DB 삭제.
	 * @param tableName
	 * @param dataEntity
	 * @return
	 */
	public int deleteLocalData(String tableName, DataEntity whereEntity){
		int result = 0;
		Trx trx = Trx.getInstance();
		Connection conn;
		try {
			conn = trx.getLocalConn();
			result = deleteData(conn, tableName, whereEntity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (trx != null)
					trx.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 테이블 삭제를 위한 공통 메서드
	 * @param tableName : 삭제할 테이블 명
	 * @param whereEntity : 삭제할 테이블의 where 조건이 있는 엔티티.
	 * @return
	 */
	public int deleteData(Connection conn, String tableName, DataEntity whereEntity) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		
		String[] whereColums = whereEntity.keySet().toArray(new String[0]);
		int whereColumSize = whereColums.length;
		
		StringBuffer values = new StringBuffer();
		
		try {
			
			sql.append("DELETE FROM "+ tableName + " WHERE \n");
			
			for(int val=0; val < whereColumSize; val++){
				sql.append(whereColums[val]+"=?");
				if(val < whereColumSize-1){ sql.append("AND \n"); }
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int stm=0; stm < whereColumSize; stm++){
				pstmt.setString((stm+1), whereEntity.get(whereColums[stm]));
				//오류 출력을 위한 value 저장
				values.append(whereEntity.get(whereColums[stm]));
				if(stm < whereColumSize-1){ values.append(", "); }
			}
			
			result = pstmt.executeUpdate();

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			System.out.println("SQL : " + sql.toString());
			System.out.println("Values : " + values.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return result;
	}
	
}
