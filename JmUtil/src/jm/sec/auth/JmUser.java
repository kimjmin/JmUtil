package jm.sec.auth;

import jm.net.DataEntity;

public class JmUser {

	private String id;
	private String passwd;
	private String name;
	
	public String getId() {
		return id;
	}
	public String getPasswd() {
		return passwd;
	}
	public String getName() {
		return name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public DataEntity getDataEntity(){
		DataEntity dataEntity = new DataEntity();
		
		dataEntity.put("id", this.id);
		dataEntity.put("passwd", this.passwd);
		dataEntity.put("name", this.name);
		
		return dataEntity;
	}
	
	public void setDataEntity(DataEntity dataEntity){
		this.id = (String)dataEntity.get("id");
		this.passwd = (String)dataEntity.get("passwd");
		this.name = (String)dataEntity.get("name");
	}
}
