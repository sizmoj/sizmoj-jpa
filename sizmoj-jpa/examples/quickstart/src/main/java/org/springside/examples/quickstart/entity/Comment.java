package org.springside.examples.quickstart.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;




/**
 * 评论Comment
 * @author sizmoj
 * @version 2013-12-11
 */
@Entity
@Table(name = "Comment")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment extends IdEntity{
	/**文章**/
	private Post post;
	/**作者**/
	private String author;
	/**评论者邮箱**/
	private String email;
	/**评论者主页**/
	private String url;
	/**评论者IP**/
	private String ip;
	/**评论时间**/
	private Date datetime;
	/**评论者内容**/
	private String text;
	/**是否被允许**/
	private String approved;
	/**评论客户端信息**/
	private String agent;
	
	
	
	public Comment() {
		super();
	}
	@ManyToOne
	@JoinColumn(name = "POST_ID")
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	@NotBlank
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
		
}
