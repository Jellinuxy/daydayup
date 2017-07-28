package com.jellysoft.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jellysoft.model.User;
import com.jellysoft.repository.UserRepository;
import com.mysql.jdbc.StringUtils;

@RestController
@RequestMapping( "/phone" )
public class UserRestController extends PhoneBaseController{
	
	
	@Autowired
	RedisTemplate redisTemplate;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 用户登录
	 * @param phone 手机号码
	 * @param pwd 密码(MD5过的)
	 * @return
	 */
	@RequestMapping( value = "/login" , method = RequestMethod.GET )
	public String login( String phone , String pwd ){
		
		//1.判空 
		if(StringUtils.isNullOrEmpty( phone ) || StringUtils.isNullOrEmpty( pwd )){
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
		
		//TODO这个地方处理用户Token 保存redis然后返回给用户 key应该是用户的phone以及uid
	
		String tokenKey = user.getTokenKey();
		String token = user.createToken();
		
		
		//保存到redis
		ValueOperations<String,String> redis = redisTemplate.opsForValue();
		redis.set( tokenKey , token );
		
		Map<String,Object> map = new HashMap<>();
		map.put( "phone" , user.phone );
		map.put( "uid" , user.uid );
		map.put( "token" , token );
		
		return backData( BackType.SUCCESS , map );
		
	}
	
}
