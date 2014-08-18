package jm.com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	
	/**
	 * 패스워드 등을 SHA 256 으로 암호화.
	 * @param rawData
	 * @return
	 */
	public static String getSha256(String rawData){
		String passwd = "";
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(rawData.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			passwd = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
		}
		return passwd;
	}
}
