package com.xsh.dao;

import com.xsh.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-12 - 1:02
 * @describe:  JpaSpecificationExecutor封装了复杂查询和方法
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {



    @Query("select b from Blog b where b.published=true and b in(select b from Blog b where b.title like ?1 or b.description like ?1)")
    Page<Blog> findByQuery(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /*在归档模块展示博客*/
    /*获取博客年份并显示年份*/
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();
    /*将博客按年份分组*/
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 and b.published=true")
    List<Blog> findByYear(String year);

    /*查询推荐博客展示在首页最新推荐模块*/
    @Query("select b from Blog b where b.recommend=true and b.published=true")
    List<Blog> findTop(Pageable pageable);


    //博客状态为草稿时，前端不展示
    @Override
    @Query("select b from Blog b where b.published=true")
    Page<Blog> findAll(Pageable pageable);

    /*查询最新博客*/
    @Query("select b from Blog b where b.published=true")
    List<Blog> findNewBlog(Pageable pageable);

    /*查询博客总数量，在归档页面显示，过滤掉草稿数量*/
    @Query("select count(b.id) from Blog b where b.published=true")
    Long countBlog();


}
