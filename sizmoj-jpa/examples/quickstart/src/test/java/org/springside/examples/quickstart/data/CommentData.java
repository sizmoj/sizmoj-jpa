package org.springside.examples.quickstart.data;

import java.util.Date;

import org.springside.examples.quickstart.entity.Comment;
import org.springside.examples.quickstart.entity.Post;
import org.springside.modules.test.data.RandomData;

public class CommentData {
	public static Comment randomComment() {
		Comment comment = new Comment();
		Post post = new Post();
		post.setId(205L);
		comment.setPost(post);
		comment.setAuthor(randomAuthor());
		comment.setEmail(randomEmail());
		comment.setUrl(randomUrl());
		comment.setIp(randomIp());
		comment.setDatetime(new Date());
		comment.setText(randomText());
		comment.setAgent(randomAgent());
		comment.setApproved("0");
		Comment pcomment = new Comment();
		pcomment.setId(1L);;
		return comment;
	}
	private static String randomAgent() {
		return RandomData.randomName("Agent");
	}
	private static String randomText() {
		return RandomData.randomName("Text");
	}
	private static String randomIp() {
		return RandomData.randomName("ip");
	}
	private static String randomUrl() {
		return RandomData.randomName("url");
	}
	public static String randomAuthor() {
		return RandomData.randomName("author");
	}
	public static String randomEmail() {
		return RandomData.randomName("dsadsa@email.com");
	}
}
