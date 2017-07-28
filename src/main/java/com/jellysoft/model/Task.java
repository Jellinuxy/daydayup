package com.jellysoft.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int task_id;
	
	public int from_id;

	public int task_category;

	@Column( length=40 )
	public String task_name;
	
	public int task_status;
	
	public Date task_create_time;
	
	@Column( length=600 )
	public String task_desc;
	
}

