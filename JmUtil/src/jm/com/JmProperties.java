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
			// 한글이 깨지는 문제 때문에 인코딩을 지정해서 읽을수 있도록 함.
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

}
