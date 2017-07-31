package com.jellysoft.repository;

import java.util.List;

import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jellysoft.model.Task;

@Repository
@Table(name="tasks")
@Qualifier("taskRepository")
public interface TaskRepository  extends PagingAndSortingRepository<Task ,Integer>{
	
	List<Task> findByLockedAndTaskstatusAndGeocodeIn( int locked , int status , List<String> in ,Pageable pageable);
	List<Task> findByLockedAndTaskstatus( int locked , int status ,Pageable pageable);
	
	
}