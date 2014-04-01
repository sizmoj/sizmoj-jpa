package org.springside.examples.quickstart.data;

import java.util.Date;

import org.springside.examples.quickstart.entity.Post;
import org.springside.examples.quickstart.entity.Term;
import org.springside.examples.quickstart.entity.User;
import org.springside.modules.test.data.RandomData;

/**
 * Post相关实体测试数据生成.
 * 
 * @author biggernin
 */
public class PostData {
	public static Post randomPost() {
		Post post = new Post();
		post.setPublicDate(new Date());
		post.setContent(randomContent());
		post.setPostTitle(randomPostTitle());
		post.setPassword(randomPassword());
		post.setStatus("0");
		post.setPostModified(new Date());
		Post parentPost = new Post();
		parentPost.setId(1L);
		Term term = new Term();
		post.setCommentCount(randomCommentCount());
		term.setId(1L);
		post.setTerm(term);
		return post;
	}
	public static String randomContent() {
		return RandomData.randomName("文章内容");
	}
	public static String randomPostTitle() {
		return RandomData.randomName("文章标题");
	}
	public static Long randomMenuOrder() {
		return RandomData.randomId();
	}
	public static String randomPassword() {
		return RandomData.randomName("password");
	}
	public static Long randomCommentCount() {
		return RandomData.randomId();
	}
}
