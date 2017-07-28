package com.jellysoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_devices_info")
public class UserDeviceInfo {

	@Id
	public int uid;
	
	@Column( length = 100 )
	public String devices;
	
	@Column( length = 10 )
	public String os;
	
}
