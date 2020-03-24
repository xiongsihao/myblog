package com.xsh.pojo;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-07 - 22:54
 * @describe: 博客标签实体类
 */
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>();
    public Tag() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
