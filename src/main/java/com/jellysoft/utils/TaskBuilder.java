package com.jellysoft.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.jellysoft.controller.PhoneBaseController.BackType;
import com.jellysoft.model.Task;

import ch.hsr.geohash.GeoHash;

public class TaskBuilder {

	
	/**
	 * 查询 
	 * @param lat
	 * @param lng
	 * @param page
	 * @param status -1 表示全部任务
	 * @return
	 */
	public static String pullTask(double lat, double lng, int page , int status) {
		
		List<Task> taskList = null;
		/* 获取数据每次10个 */
		if( status != -1 ){
			GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lng, 6);
			String currentBase32 = geoHash.toBase32();
			GeoHash[] adjacent = geoHash.getAdjacent();
			List<String> base32s = new ArrayList<>();
			base32s.add(currentBase32);
			for (int i = 0; i < adjacent.length; i++) {
				base32s.add(adjacent[i].toBase32());
			}
			
			taskList = taskRepository.findByLockedAndTaskstatusAndGeocodeIn( Task.TASK_NOT_LOCK , status ,base32s, new PageRequest(page, 10));
		}
		else{
			taskList = taskRepository.findByLockedAndTaskstatus( Task.TASK_NOT_LOCK , status, new PageRequest(page, 10) );
		}
		
	
		
		Map<String, Object> map = new HashMap<>();
		map.put("tasks", taskList);
		map.put("page", page);
		map.put("last", taskList.size() == 10 ? 0 : 1);// 如果返回的数据不是10条那么表示他只最后一页了。
		return backData(BackType.SUCCESS, map);

	}
	
}
