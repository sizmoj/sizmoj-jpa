package org.springside.examples.quickstart.service.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.quickstart.entity.Comment;
import org.springside.examples.quickstart.entity.Post;
import org.springside.examples.quickstart.entity.Tag;
import org.springside.examples.quickstart.entity.Term;
import org.springside.examples.quickstart.repository.CommentDao;
import org.springside.examples.quickstart.repository.PostDao;
import org.springside.examples.quickstart.repository.TagDao;
import org.springside.examples.quickstart.repository.TermDao;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.Clock;
//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class PostService {
	private static Logger logger = LoggerFactory.getLogger(PostService.class);
	private CommentDao commentDao;
	private PostDao postDao;
	private TagDao tagDao;
	private TermDao termDao;
	private Clock clock = Clock.DEFAULT;
	
	@PersistenceContext
	private EntityManager em;
	
	public Page<Post> getPostList(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Post> spec = buildSpecification(searchParams);
		return postDao.findAll(spec, pageRequest);
	}
	
	public Page<Comment> getCommentList(Long id, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize);
		Specification<Comment> spec = buildSpecificationComment(searchParams, id);
		return commentDao.findAll(spec, pageRequest);
	}
	
	public Page<Comment> getAllCommentList( Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		Sort sort = new Sort(Direction.DESC, "datetime");
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, sort);
		return commentDao.findAll(pageRequest);
	}
	
	
	public void savePost(Post post) {
		Post postOr = new Post();
		if(post.getId() == null) {
			post.setPublicDate(clock.getCurrentDate());
		} else {
			postOr = postDao.findOne(post.getId());
		}
		post.setStatus("0");
		post.setCommentCount(0L);
		if(post.getTerm().getId() != postOr.getTerm().getId()) {
			Term term = termDao.findOne(post.getTerm().getId());
			term.setPostCount(term.getPostCount()-1);
			term.setPostCount(term.getPostCount()-1);
			Term term2 = termDao.findOne(postOr.getTerm().getId());
			term2.setPostCount(term2.getPostCount()-1);
			term2.setPostCount(term2.getPostCount()-1);
		}
		post.setPostModified(clock.getCurrentDate());
		List<Tag> tags = StringToTagList(post.getTagsStringByOr());
		Term term = termDao.findOne(post.getTerm().getId());
		post.setTerm(term);
		for(Tag tag : tags) {
			em.clear();
			Tag t =  tagDao.findByName(tag.getName());
			if(t == null) {
				tag = tagDao.save(tag);
				tag.setId(tag.getId());
			} else {
				tag.setId(t.getId());
			}
		}
		em.clear();
		post.setTags(tags);
		postDao.save(post);
	}
	
	public List<Term> getAllTerm() {
		return (List<Term>)termDao.findAll();
	}
	
	public Post getPost(Long id) {
		return postDao.findOne(id);
	}
	
	public void deletePost(Long id) {
		postDao.deletePost(id);
		
	}
	
	public void deleteComment(Long id) {
		commentDao.delete(id);
		
	}	
	
	public void saveTerm(Term term) {
		termDao.save(term);
		
	}	
	
	
	
	//--------------- private method
	/**
	 * 
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		sort = new Sort(Direction.DESC, "publicDate");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Post> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("status", new SearchFilter("status", Operator.LIKE, "0"));
		Specification<Post> spec = DynamicSpecifications.bySearchFilter(filters.values(), Post.class);
		return spec;
	}
	
	/**
	 * 创建动态查询条件组合for Comment.
	 */
	private Specification<Comment> buildSpecificationComment(Map<String, Object> searchParams,Long id) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("post.id", new SearchFilter("post.id", Operator.EQ, id));
		Specification<Comment> spec = DynamicSpecifications.bySearchFilter(filters.values(), Comment.class);
		return spec;
	}
	
	/**
	 * 
	 * @param tags
	 * @return
	 */
	private List<Tag> StringToTagList(String tags) {
		List<Tag> ts = new ArrayList<Tag>();
		String[] ss = tags.split(";");
		Tag temp;
		for(String s : ss) {
			if(StringUtils.isNoneBlank(s)) {
				temp = new Tag();
				temp.setName(s);
				ts.add(temp);
			}
		}
		return ts;
	}
	
	//-----------------------------get set
	
	
	
	
	
	public CommentDao getCommentDao() {
		return commentDao;
	}
	@Autowired
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	public PostDao getPostDao() {
		return postDao;
	}
	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
	public TagDao getTagDao() {
		return tagDao;
	}
	@Autowired
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
	public TermDao getTermDao() {
		return termDao;
	}
	@Autowired
	public void setTermDao(TermDao termDao) {
		this.termDao = termDao;
	}
	public Clock getClock() {
		return clock;
	}
	public void setClock(Clock clock) {
		this.clock = clock;
	}	
}
