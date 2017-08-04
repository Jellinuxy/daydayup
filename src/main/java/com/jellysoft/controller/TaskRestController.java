package com.jellysoft.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jellysoft.controller.PhoneBaseController.BackType;
import com.jellysoft.model.Task;
import com.jellysoft.repository.TaskRepository;
import com.jellysoft.utils.TextUtils;
import com.spatial4j.core.io.GeohashUtils;

import ch.hsr.geohash.GeoHash;

@RestController
@RequestMapping("/task")
public class TaskRestController extends PhoneBaseController {

	private int radiusValue = 6;

	//每次拿多少条数据
	private static final int TASK_SELECT_COUNT = 10;
	
	
	@Autowired
	TaskRepository taskRepository;

	/**
	 * 用户发布任务
	 * 
	 * @param taskname
	 *            任务名称
	 * @param taskdesc
	 *            任务描述
	 * @param cate
	 *            任务类型
	 * @return
	 */
	@RequestMapping(value = "/push", method = RequestMethod.GET)
	public String pushTask(String taskname, String taskdesc, String pics, int cate, double lat, double lng) {

		// // 验证用户
		if (!checkToken()) {
			// 用户token错误
			return backData(BackType.TOKEN_ERROR);
		}
		1
		//任务名称，以及任务描述不能为空
		if (TextUtils.isNullOrEmpty(taskname, taskdesc)) {
			return backData(BackType.PUSH_TASK_EMPTY);
		}

		String geo = GeohashUtils.encodeLatLon(lat, lng, radiusValue);

		Task task = new Task();
		task.cate_id = cate;
		task.from_id = 1;// Integer.parseInt(getUid()); 记得修改回去，当加入Token了之后
		task.geocode = geo;
		task.task_create_time = new Date();
		task.task_desc = taskdesc;
		task.task_name = taskname;
		task.lat = lat;
		task.lng = lng;
		task.pics = pics;// 多张以,号隔开
		task.locked = 0;
		// 保存任务
		taskRepository.save(task);
		return backData(BackType.SUCCESS);

	}

	/**
	 * 获取任务
	 * 
	 * @param lat
	 * @param lng
	 * @param isnearly
	 *            是否是附近任务
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/pull_area", method = RequestMethod.GET)
	public String pullTask(double lat, double lng, int isnearly, int page) {

		List<Task> taskList = null;
		PageRequest pageRequest = new PageRequest(page, TASK_SELECT_COUNT);
		if (isnearly == 1) {
			// 查询附近的
			GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lng, 6);
			String currentBase32 = geoHash.toBase32();
			GeoHash[] adjacent = geoHash.getAdjacent();
			List<String> base32s = new ArrayList<>();
			base32s.add(currentBase32);
			for (int i = 0; i < adjacent.length; i++) {
				base32s.add(adjacent[i].toBase32());
			}

			/* 获取数据每次10个 */
			taskList = taskRepository.findByLockedAndTaskstatusAndGeocodeIn(Task.TASK_NOT_LOCK, Task.TASK_NO_DO,
					base32s, pageRequest);
		} else {
			// 查询所有的
			taskList = taskRepository.findByLockedAndTaskstatus(Task.TASK_NOT_LOCK, Task.TASK_NO_DO, pageRequest);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("tasks", taskList);
		map.put("page", page);
		map.put("last", taskList.size() == 10 ? 0 : 1);// 如果返回的数据不是10条那么表示他只最后一页了。
		return backData(BackType.SUCCESS, map);

	}

}