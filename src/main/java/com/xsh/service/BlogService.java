package com.xsh.service;

import com.xsh.pojo.Blog;
import com.xsh.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author : xsh
 * @create : 2020-02-12 - 0:57
 * @describe:
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog); //Blog封装了查询博客的过滤条件


    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable,BlogQuery blog);

    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    List<Blog> listNewBlog(Integer size);

    Map<String,List<Blog>> archiveBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Long countBlog();
}
