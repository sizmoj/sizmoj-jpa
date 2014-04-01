package org.springside.examples.quickstart.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Tag;

public interface TagDao extends PagingAndSortingRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {
	
	@Modifying 
	@Query("delete FROM Tag t WHERE t.name = ?1")
	public void deleteByName(String name);
	
	public Tag findByName(String name);
}
