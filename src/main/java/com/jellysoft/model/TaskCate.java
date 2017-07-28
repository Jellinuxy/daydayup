package com.jellysoft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task_cate")
public class TaskCate {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int cate_id;
	
	@Column( length=120 )
	public String cate_name;
	
	
}
