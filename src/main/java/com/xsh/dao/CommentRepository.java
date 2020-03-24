package com.xsh.dao;

import com.xsh.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-15 - 0:26
 * @describe:
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
