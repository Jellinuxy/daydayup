package com.jellysoft.repository;

import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jellysoft.model.SiteInfo;

@Repository
@Table(name="site_info")
@Qualifier("siteInfoRepository")
public interface SiteInfoRepository extends CrudRepository<SiteInfo ,Integer> {
	
	//获取最后一次网站设置信息
	SiteInfo findFirstByOrderByInfoid();
	
}