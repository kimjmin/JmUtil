package jm.net;

import java.util.HashMap;

import jm.util.StringUtil;

public class DataEntity extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 987170914166863205L;

	public int getLength(){
		return this.size();
	}
	
	public String[] getKeys(){
		String[] keys = this.keySet().toArray(new String[0]);
		return keys;
	}
	
	/**
	 * 하나의 DataEntity를 JSON 형식의 문자열로 리턴. 자기 자신.
	 * @return
	 */
	public String toJson(){
		StringBuffer str = new StringBuffer();
		str.append("{");
		String[] keys = this.getKeys();
		String keyVal = "";
		for(int i=0; i < keys.length; i++){
			keyVal = this.get(keys[i])+"";
			if("null".equals(keyVal.toLowerCase())){
				str.append("\""+keys[i]+"\":null");
			} else if(StringUtil.isNumber(keyVal)){
				str.append("\""+keys[i]+"\":"+keyVal);
			} else {
				str.append("\""+keys[i]+"\":\""+keyVal+"\"");
			}
			if(i < keys.length-1){ str.append(","); }
		}
		str.append("}");
		
		return str.toString();
	}
	
	/**
	 * 하나의 DataEntity를 JSON 형식의 문자열로 리턴. 주어진 DataEntity
	 * @param dataEntity
	 * @return
	 */
	public String toJson(DataEntity dataEntity){
		StringBuffer str = new StringBuffer();
		str.append("{");
		String[] keys = dataEntity.getKeys();
		String keyVal = "";
		for(int i=0; i < keys.length; i++){
			keyVal = dataEntity.get(keys[i])+"";
			if("null".equals(keyVal.toLowerCase())){
				str.append("\""+keys[i]+"\":null");
			} else if(StringUtil.isNumber(keyVal)){
				str.append("\""+keys[i]+"\":"+keyVal);
			} else {
				str.append("\""+keys[i]+"\":\""+keyVal+"\"");
			}
			if(i < keys.length-1){ str.append(","); }
		}
		str.append("}");
		
		return str.toString();
	}
	
	/**
	 * 하나의 DataEntity를 JSON 형식의 문자열로 리턴. 자기 자신.
	 * { } 중괄호 없이 값만 리턴. 해당 데이터 이후 다른 데이터 추가할 필요가 있을 때 용이함.
	 * @param dataEntity
	 * @return
	 */
	public String toJsonNobrc(){
		StringBuffer str = new StringBuffer();
		String[] keys = this.getKeys();
		String keyVal = "";
		for(int i=0; i < keys.length; i++){
			keyVal = this.get(keys[i])+"";
			if("null".equals(keyVal.toLowerCase())){
				str.append("\""+keys[i]+"\":null");
			} else if(StringUtil.isNumber(keyVal)){
				str.append("\""+keys[i]+"\":"+keyVal);
			} else {
				str.append("\""+keys[i]+"\":\""+keyVal+"\"");
			}
			if(i < keys.length-1){ str.append(","); }
		}
		
		return str.toString();
	}
	
	/**
	 * 하나의 DataEntity를 JSON 형식의 문자열로 리턴. 주어진 DataEntity
	 * { } 중괄호 없이 값만 리턴. 해당 데이터 이후 다른 데이터 추가할 필요가 있을 때 용이함.
	 * @param dataEntity
	 * @return
	 */
	public String toJsonNobrc(DataEntity dataEntity){
		StringBuffer str = new StringBuffer();
		String[] keys = dataEntity.getKeys();
		String keyVal = "";
		for(int i=0; i < keys.length; i++){
			keyVal = dataEntity.get(keys[i])+"";
			if("null".equals(keyVal.toLowerCase())){
				str.append("\""+keys[i]+"\":null");
			} else if(StringUtil.isNumber(keyVal)){
				str.append("\""+keys[i]+"\":"+keyVal);
			} else {
				str.append("\""+keys[i]+"\":\""+keyVal+"\"");
			}
			if(i < keys.length-1){ str.append(","); }
		}
		
		return str.toString();
	}
	
	/**
	 * 복수의 DataEntity를 배열 형식의 JSON 문자열로 리턴
	 * @param dataEntitys
	 * @return
	 */
	public static String toJsonArray(DataEntity[] dataEntitys){
		StringBuffer str = new StringBuffer();
		str.append("[");
		for(int d=0; d < dataEntitys.length; d++){
			DataEntity dataEntity = dataEntitys[d];
			str.append("{");
			String[] keys = dataEntity.getKeys();
			String keyVal = "";
			for(int i=0; i < keys.length; i++){
				keyVal = dataEntity.get(keys[i])+"";
				if("null".equals(keyVal.toLowerCase())){
					str.append("\""+keys[i]+"\":null");
				} else if(StringUtil.isNumber(keyVal)){
					str.append("\""+keys[i]+"\":"+keyVal);
				} else {
					str.append("\""+keys[i]+"\":\""+keyVal+"\"");
				}
				if(i < keys.length-1){ str.append(","); }
			}
			str.append("}");
			if(d < dataEntitys.length-1){ str.append(","); }
		}
		str.append("]");
		return str.toString();
	}
	
}
