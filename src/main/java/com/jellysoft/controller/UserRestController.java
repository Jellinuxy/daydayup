package com.jellysoft.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jellysoft.model.User;
import com.jellysoft.model.UserInfo;
import com.jellysoft.repository.UserInfoRepository;
import com.jellysoft.repository.UserRepository;
import com.jellysoft.utils.TextUtils;
import com.mysql.jdbc.StringUtils;

/**
 * 手机用户相关模块Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping( "/phone" )
public class UserRestController extends PhoneBaseController{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	/**
	 * 用户登录
	 * @param phone 手机号码
	 * @param pwd 密码(MD5过的)
	 * @return
	 */
	@RequestMapping( value = "/login" , method = RequestMethod.GET )
	public String login( String phone , String pwd ){
		
		if( TextUtils.isNullOrEmpty( phone , pwd ) ){
			return backData( BackType.LOGIN_EMPTY );
		}
				
		//2.查询数据库
		User user = userRepository.findByPhoneAndPwd( phone , pwd);
		
		//判断账号密码是否正确
		if( user == null ){
			return backData( BackType.LOGIN_ERROR );
		}
		
		//判断用户是否被锁定
		if( user.isLocked() ){
			return backData( BackType.LOGIN_LOCKED );
		}
		
		//更新最后一次登录时间
		userRepository.updateLastLoginTime( user.uid , new Date() );
		
		//保存用户token
		String token = saveToken( user );
		
		Map<String,Object> map = new HashMap<>();
		map.put( "phone" , user.phone );
		map.put( "uid" , user.uid );
		map.put( "token" , token );
		map.put( "nickname" , user.userInfo.nickname );
		map.put( "head_url",user.userInfo.head_url );
		map.put( "desc" ,user.userInfo.description );
		map.put( "sex" , user.userInfo.sex );
		return backData( BackType.SUCCESS , map );
	}
	
	/**
	 * 注册接口
	 * @param phone 电话号码
	 * @param pwd   密码，已经MD5过
	 * @param sex   性别  1 男         2 女
	 * @param header_url  头像url
	 * @param description 简单的自我介绍
	 * @return
	 */
	@RequestMapping( value = "/register" , method = RequestMethod.GET )
	public String register( String phone , String pwd , int sex , String header_url , String description , String nickname ){
		
		//1.判空 
		if( TextUtils.isNullOrEmpty( phone , pwd , header_url , description , nickname ) ){
			return backData( BackType.REGISTER_EMPTY );
		}
		
		//2.判断该电话号码是否已经存在
		User existUser = userRepository.findByPhone( phone );
		if( existUser != null ){
			return backData( BackType.REGISTER_USER_EXIST );
		}
		
		//添加User
		User user = new User();
		user.phone = phone;
		user.pwd = pwd;
		user.last_login = new Date();
		user.register_time = new Date();
		
		//保存用户
		userRepository.save( user );
		
		//保存用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.description = description;
		userInfo.uid = user.uid;
		userInfo.sex = sex;
		userInfo.head_url = header_url;
		userInfo.nickname = nickname;
		userInfo.id_code = "";
		userInfo.real_name = "";
		userInfoRepository.save( userInfo );
		
		//保存token
		String token = saveToken( user );
		
		Map<String,Object> map = new HashMap<>();
		map.put( "phone" , user.phone );
		map.put( "uid" , user.uid );
		map.put( "token" , token );
		map.put( "nickname" , userInfo.nickname );
		map.put( "head_url",userInfo.head_url );
		map.put( "desc" ,userInfo.description );
		map.put( "sex" , userInfo.sex );
		
		return backData( BackType.SUCCESS , map );
	}
	
	
}