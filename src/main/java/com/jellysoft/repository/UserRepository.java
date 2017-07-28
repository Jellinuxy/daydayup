package com.jellysoft.repository;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jellysoft.model.User;

@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User ,Integer>{
	
	User findByPhoneAndPwd( String phone , String pwd );
	
	@Query("UPDATE User u SET u.last_login=?2 WHERE u.uid=?1")
	@Modifying
	@Transactional
	void updateLastLoginTime( int uid , Date lastLoginTime);
	
	
}