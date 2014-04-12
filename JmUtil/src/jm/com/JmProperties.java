package jm.com;

import java.io.InputStreamReader;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JmProperties {

	private Properties property;

	public JmProperties() {
		this.property = new Properties();
		try {
			this.property.load(new BufferedReader(new InputStreamReader(
					new FileInputStream(".net.property"), "UTF-8")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String get(String key) {
		return property.getProperty(key);
	}

	/*
	public static void main(String args[]) {
		JmProperties property = new JmProperties();
		System.out.println(property.get("name"));
 
	}
	*/
}
