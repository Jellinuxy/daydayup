package com.jellysoft.repository;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jellysoft.model.UserInfo;

@Repository
@Table(name="user_info")
@Qualifier("userInfoRepository")
public interface UserInfoRepository extends CrudRepository<UserInfo ,Integer>{
	
	
	
}
