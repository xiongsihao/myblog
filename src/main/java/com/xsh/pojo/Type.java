package com.xsh.pojo;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-07 - 22:51
 * @describe: 博客类型(分类)实体类
 */
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type") //指定关系为一对多,mappedBy声明当前是被维护端，通过type连接
    private List<Blog> blogs=new ArrayList<>(); //一个分类下可以有很多博客
    public Type() {
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
