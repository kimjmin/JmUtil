package jm.com;

import java.io.InputStreamReader;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class JmProperties {

	private Properties property;

	public JmProperties(){
		this.property = new Properties();
	}
	
	public JmProperties(String propertyFile) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		this.property = new Properties();
		this.property.load(new BufferedReader(new InputStreamReader(new FileInputStream(propertyFile), "UTF-8")));
	}
	
	public boolean setPath(String path){
		boolean created = false;
		try {
			this.property.load(new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")));
			created = true;
		} catch (UnsupportedEncodingException e) {
			System.out.println("UTF-8 : 지원하지 않는 형식입니다.");
			created = false;
		} catch (FileNotFoundException e) {
			System.out.println("파일이 존재하지 않습니다 :\n"+path);
			created = false;
		} catch (IOException e) {
			created = false;
		}
		return created;
	}
	
	public boolean setResource(String resource) {
		boolean created = false;
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
		try {
			this.property.load(inputStream);
			created = true;
		} catch (Exception e) {
			created = false;
		}
        return created;
	}
	
	public void set(String key, String value) {
		this.property.setProperty(key, value);
	}
	
	public String get(String key) {
		return this.property.getProperty(key);
	}
	
	public void save(){
		
	}
	
}
