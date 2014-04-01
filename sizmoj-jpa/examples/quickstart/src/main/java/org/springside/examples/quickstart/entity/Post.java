package org.springside.examples.quickstart.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * 评论Comment
 * @author sizmoj
 * @version 2013-12-11
 */
@Entity
@Table(name = "POST")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post extends IdEntity{
	
	/**发布时间**/
	private Date publicDate;
	/**文章内容**/
	private String content;
	/**文章标题**/
	private String postTitle;
	/**文章状态**/
	private String status;
	/**文章密码**/
	private String password;
	/**修改时间**/
	private Date postModified;
	/**留言总数**/
	private Long commentCount;
	/**所属分类**/
	private Term term;
	/**url链接**/
	private String url;
	/**标签**/
	private List<Tag> tags = new ArrayList<Tag>();
	
	private List<Comment> comments = new ArrayList<Comment>();
	
	private String tagsString;
	
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	@Transient
	public String getTagsString() {
		return tagsToString(getTags());
	}
	@Transient
	public String getTagsStringByOr() {
		return tagsString;
	}
	@Transient
	public void setTagsString(String tagsString) {
		this.tagsString = tagsString;
	}
	public Post() {
		super();
	}
	public Post(User author, Date publicDate, String content,
			String postTitle, String excerpt, String status, String password,
			Date postModified, Post parentPost, int menuOrder,
			Long commentCount, Term term, List<Tag> tags) {
		super();
		this.publicDate = publicDate;
		this.content = content;
		this.postTitle = postTitle;
		this.status = status;
		this.password = password;
		this.postModified = postModified;
		this.commentCount = commentCount;
		this.term = term;
		this.tags = tags;
	}
	public Date getPublicDate() {
		return publicDate;
	}
	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getPostModified() {
		return postModified;
	}
	public void setPostModified(Date postModified) {
		this.postModified = postModified;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "term_id")
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	// 多对多定义
	@ManyToMany
	@JoinTable(name = "POST_TAG", joinColumns = { @JoinColumn(name = "POST_ID") }, inverseJoinColumns = { @JoinColumn(name = "TAG_ID") })
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	@OneToMany
	@JoinColumn(name="POST_ID")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
		
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Transient
	public String tagsToString(List<Tag> tags) {
		StringBuilder sb = new StringBuilder();
		if(tags.size() == 0) {
			return null;
		} else {
			for(Tag tag: tags) {
				sb.append(tag.getName() + ";");
			}
			return sb.toString();
		}
	}	
}
