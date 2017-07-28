package com.jellysoft.repository;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jellysoft.model.User;

@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User ,Integer>{
	
	User findByPhoneAndPwd( String phone , String pwd );
	
}
