package com.jellysoft.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.jellysoft.utils.TextUtils;

@Entity
@Table(name="user")
public class User {
	
	public static final String TOKEN_STATIC = "$$!!@##!@#!@###IUjaasccc";
	public static final String PREFIX = "token:";
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	public int uid;
	
	@Column( length=20 )
	public String phone;
	
	@Column( length=40 )
	public String pwd;
	
	public Date register_time;
	
	public Date last_login;
	
	public int 	is_locked;
	
	
	@OneToOne //JPA注释： 一对一 关系
    @JoinColumn(name="uid" )// 在pepole中，添加一个外键 "uid"
	public UserInfo userInfo;
	
	/**
	 * 返回用户是否被锁定
	 * @return true 被锁定了  false 没有
	 */
	public boolean isLocked(){
		return is_locked == 1 ? true : false;
	}
	
	/**
	 * 获取当前用户的key 以保存他的token
	 * @param time 登录的时间戳
	 * @return
	 */
	public String getTokenKey(){
		String md5 = TextUtils.getMD5( phone + "_" + uid + "_");
		return PREFIX + md5;
		
	}
	
	/**
	 * 获取当前用户的key 以保存他的token
	 * @param phone 
	 * @param uid
	 * @return
	 */
	public static String getTokenKey( String phone , String uid  ){
		String md5 = TextUtils.getMD5( phone + "_" + uid + "_");
		return PREFIX + md5;
	}
	
	/**
	 * 创建token
	 * @param time 创建时的时间戳
	 * @return
	 */
	public String createToken(){
		long currentTime = System.currentTimeMillis();//当前时间戳
		String orinalString = uid + phone + register_time.getTime() + TOKEN_STATIC + currentTime;
		return TextUtils.getMD5( orinalString);
	}
	
}
