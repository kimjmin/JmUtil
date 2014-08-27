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

	public JmProperties() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		this.property = new Properties();
	}
	
	public JmProperties(String propertyFile) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		this.property = new Properties();
		this.property.load(new BufferedReader(new InputStreamReader(new FileInputStream(propertyFile), "UTF-8")));
	}
	
	public void setPath(String path) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		this.property.load(new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8")));
	}
	public void setResource(String resource) throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
		this.property.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + resource + "' not found in the classpath");
        }
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
