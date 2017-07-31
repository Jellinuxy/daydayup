package com.jellysoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="user_info")
public class UserInfo {

	@Id
	public int uid;
	
	@Column( length=30 )
	public String real_name;
	
	@Column( length=30 )
	public String nickname;
	
	@Column( length=30 )
	public String id_code;
	
	public int sex;
	
	@Column( length=100 )
	public String head_url;
	
	@Column( length=500 )
	public String description;
	

	
}