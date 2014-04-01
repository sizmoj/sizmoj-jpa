package org.springside.examples.quickstart.service.post;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.quickstart.data.PostData;
import org.springside.examples.quickstart.entity.Post;
import org.springside.examples.quickstart.repository.PostDao;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class PostServiceTest extends SpringTransactionalTestCase{
	@Autowired
	private PostService postService;
	@Autowired
	private PostDao postDao;
	
	
	@Test
	@Rollback(value= false)
	public void testGetPost() {
		for (int i = 0; i < 10; i++) {
			postDao.save(PostData.randomPost());
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("LIKE_postTitle", "文章标题8fdsfds");
		Page<Post> p =  postService.getPostList(searchParams, 2, 2, null);
		for (Post post : p.getContent()) {
			System.out.println(post.getPostTitle());
		}
		System.out.println(p.getContent().size());
	}

}
