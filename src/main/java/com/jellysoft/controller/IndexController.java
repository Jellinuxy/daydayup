package com.jellysoft.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jellysoft.model.SiteInfo;
import com.jellysoft.repository.SiteInfoRepository;

@Controller
public class IndexController {
	
	@Autowired
	private SiteInfoRepository siteInfoRepository;
	
	@RequestMapping("/")
	public String index( Map<String,Object> params ){
		a
		SiteInfo siteInfo = siteInfoRepository.findFirstByOrderByInfoid();
		params.put( "siteinfo" , siteInfo );
		return "index";
	}
	
}