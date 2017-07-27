package com.jellysoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="site_info")
public class SiteInfo {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int infoid;
	
	//网站名称
	public String site_name;
	
	//网站描述
	public String site_desc;
	
	//网站标题
	public String site_title;
	
}
