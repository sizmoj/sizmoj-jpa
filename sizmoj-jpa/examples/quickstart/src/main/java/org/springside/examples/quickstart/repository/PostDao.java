package org.springside.examples.quickstart.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Post;

public interface PostDao extends PagingAndSortingRepository<Post, Long>, JpaSpecificationExecutor<Post>{
	
	@Modifying
	@Query("UPDATE Post p set p.status = '1' where p.id=?1")
	void deletePost(Long id);

	
}
