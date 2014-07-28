package jm.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import jm.com.JmProperties;

public class Log {
	
	private static JmProperties property = null;
	
	public static Log instance = null;
	private Log(){}
	public static Log getInstance(String prop){
		property = new JmProperties(prop);
		if(instance == null){
			instance = new Log();
		}
		return instance;
		
	}
	
	public void debug(String msg){
		SimpleDateFormat logY = new SimpleDateFormat("yyyy", Locale.KOREA);
		SimpleDateFormat logM = new SimpleDateFormat("MM", Locale.KOREA);
		File tempPath = new File( (property.get("logPath") + "/"+logY.format(new Date())+"/"+logM.format(new Date())+"/").replaceAll("//","/") );
		if(!tempPath.exists()){
			tempPath.mkdirs(); 
		}
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			fw = new FileWriter(tempPath+"/"+formatter.format(new Date())+".log", true);
			bw = new BufferedWriter(fw);
			bw.write(msg);
		    bw.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
