package jm.net;

import java.util.HashMap;

public class DataEntity extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 987170914166863205L;

	public int getLength(){
		return this.size();
	}
	
	public String[] getKeys(){
		String[] keys = this.keySet().toArray(new String[0]);
		return keys;
	}
	
}
