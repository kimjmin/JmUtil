package jm.util;

public class StringUtil {

	/**
	 * str 값이 숫자인지 판별하는 메소드.
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		char check;
		if (str.equals("")) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			check = str.charAt(i);
			if (check < 48 || check > 58) {
				// 해당 char값이 숫자가 아닐 경우
				return false;
			}
		}
		return true;
	}
	
	/**
	 * SQL문을 출력해보기 위한 메소드.
	 * @param sql
	 * @param params
	 * @return
	 */
	public static String getSqlString(String sql, String[] params){
		String result = sql;
		int p = 0;
		while(result.indexOf("[?]") > 0 || p < params.length){
			result = result.replaceFirst("[?]", "'"+params[p]+"'");
			p++;
		}
		if(result.indexOf("[?]") > 0){
			result = "파라메터가 부족합니다.\n";
			result += "sql(?) : "+p+"\n";
			result += "params : "+params.length+"\n";
		}
		if(p < params.length){
			result = "파라메터가 남습니다.\n";
			result += "sql(?) : "+p+"\n";
			result += "params : "+params.length+"\n";
		}
		return result;
	}
	
}
