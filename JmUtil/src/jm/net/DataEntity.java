package jm.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

public class DataEntity extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 987170914166863205L;

	public int getLength(){
		return this.size();
	}
	
	public String[] getKeys(){
		Iterator iterator = this.entrySet().iterator();
		Vector<String> keys = new Vector<String>();
		while (iterator.hasNext()) {
			Entry entry = (Entry)iterator.next();
			keys.add(entry.getKey()+"");
		}
		return keys.toArray(new String[keys.size()]);
	}
	
}
