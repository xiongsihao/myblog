package com.xsh.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-07 - 22:56
 * @describe: 博客评论人信息实体类
 */
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname; //评论者昵称
    private String email; //邮箱
    @Lob
    private String content; //评论内容
    private String avatar; //头像
    @Temporal(TemporalType.TIMESTAMP)  //指定时间的类型
    private Date createTime;

    @ManyToOne
    private Blog blog;

    //评论类自关联关系，一个评论下面会有很多个回复
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>(); //回复
    @ManyToOne
    private Comment parentComment; //评论

    private Integer adminComment;//评论类型，0为游客评论，1为访客评论，2为管理员评论

    public Comment() {

    }


    public Integer getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Integer adminComment) {
        this.adminComment = adminComment;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", adminComment=" + adminComment +
                '}';
    }
}
