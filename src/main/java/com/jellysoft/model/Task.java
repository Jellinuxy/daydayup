package com.jellysoft.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {

	public static final int TASK_ALL = -1;//获取所有状态的任务
	public static final int TASK_NO_DO = 0;//任务还没有人接
	public static final int TASK_DOING_NOW = 1;//任务正在处理
	public static final int TASK_COMPLETE = 2;//任务完成
	public static final int TASK_TIME_OUT = 3;//任务过期
	
	
	public static final int TASK_NOT_LOCK = 0;//任务未被锁定
	public static final int TASK_IS_LOCKED = 1;//任务被锁定
	
	
	
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int task_id;
	
	public int from_id;

	public int cate_id;

	@Column( length=40 )
	public String task_name;
	
	@Column( name="task_status"  )
	public int taskstatus;
	
	public Date task_create_time;
	
	@Column( length=600 )
	public String task_desc;
	
	@Column( length=12 )
	public String geocode;
	
	@Column( length=150 )
	public String pics;
	
	public double lat;
	
	public double lng;
	
	public int locked;
	
//	@OneToOne //JPA注释： 一对一 关系
//    @JoinColumn(name="cate_id" )// 在task中，添加一个外键 "cate_id"
//	public TaskCate cate;
	
}

