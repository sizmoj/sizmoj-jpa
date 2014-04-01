package org.springside.examples.quickstart.web.post;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Comment;
import org.springside.examples.quickstart.entity.Post;
import org.springside.examples.quickstart.entity.Term;
import org.springside.examples.quickstart.service.post.PostService;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/post")
public class PostContoller {
	private static final String PAGE_SIZE = "5";
	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
	}
	@Autowired
	private PostService postService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Post> posts = postService.getPostList(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("posts", posts);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "post/postList";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("post", new Post());
		model.addAttribute("action", "create");
		model.addAttribute("terms", postService.getAllTerm());
		return "post/postForm";
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Post newPost, RedirectAttributes redirectAttributes) {
		postService.savePost(newPost);
		redirectAttributes.addFlashAttribute("message", "创建文章成功");
		return "redirect:/post/";
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("post", postService.getPost(id));
		model.addAttribute("terms", postService.getAllTerm());
		model.addAttribute("action", "update");
		return "post/postForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("post") Post post, RedirectAttributes redirectAttributes) {
		postService.savePost(post);
		redirectAttributes.addFlashAttribute("message", "更新文章成功");
		return "redirect:/post/";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		postService.deletePost(id);
		redirectAttributes.addFlashAttribute("message", "删除文章成功");
		return "redirect:/post/";
	}
	
	@RequestMapping(value = "lookComment/{postid}")
	public String lookComment(@PathVariable("postid") Long postid,@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Comment> comments = postService.getCommentList(postid, searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("comments", comments);
		model.addAttribute("postid", postid);
		return "post/CommentList";
	}
	
	
	@RequestMapping(value = "deleteComment/{id}/{postid}")
	public String deleteComment(@PathVariable("id") Long id, @PathVariable("postid") Long postid,
			RedirectAttributes redirectAttributes) {
		postService.deleteComment(id);
		redirectAttributes.addFlashAttribute("message", "删除留言成功");
		return "redirect:/post/lookComment/" + postid;
	}
	/**
	 * 显示所有Comment
	 */
	@RequestMapping(value = "lookCommentAll")
	public String lookAllComment(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<Comment> comments = postService.getAllCommentList(searchParams, pageNumber, pageSize, sortType);
		model.addAttribute("comments", comments);
		System.out.println("11111111111111111111");
		return "post/allCommentList";
	}
	
	@RequestMapping(value = "deleteComment/{id}")
	public String deleteCommentNotByPost(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		postService.deleteComment(id);
		redirectAttributes.addFlashAttribute("message", "删除留言成功");
		return "redirect:/post/lookCommentAll";
	}
	
	
	@RequestMapping(value = "listTerm", method = RequestMethod.GET)
	public String listTerm(Model model,ServletRequest request) {
		model.addAttribute("terms", postService.getAllTerm());
		// 将搜索条件编码成字符串，用于排序，分页的URL
		return "post/TermList";
	}
	
	@RequestMapping(value = "createTerm", method = RequestMethod.GET)
	public String createTerm(Model model) {
		model.addAttribute("term", new Term());
		model.addAttribute("action", "createTerm");
		model.addAttribute("terms", postService.getAllTerm());
		return "post/termForm";
	}
	
	@RequestMapping(value = "createTerm", method = RequestMethod.POST)
	public String ceateTerm(@RequestParam(value = "name") String name, RedirectAttributes redirectAttributes) {
		Term term = new Term();
		term.setName(name);
		term.setPostCount(0L);
		postService.saveTerm(term);
		redirectAttributes.addFlashAttribute("message", "创建分类成功");
		return "redirect:/post/listTerm";
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getTask(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("post", postService.getPost(id));
		}
	}
}
