package com.xsh.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-03-18 - 15:24
 * @describe:
 */
@Entity
@Table(name = "t_message")
public class Message {

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

    private Boolean replyInform;//留言被回复后是否发邮件通知

    private String openid;//评论人的openid，默认为空

    //评论类自关联关系，一个评论下面会有很多个回复
    @OneToMany(mappedBy = "parentMessage")
    private List<Message> replyMessages=new ArrayList<>(); //回复
    @ManyToOne
    private Message parentMessage; //评论

    private Integer adminMessage;//评论类型，0为游客评论，1为访客评论，2为管理员评论

    public Message() {

    }

    public Boolean getReplyInform() {
        return replyInform;
    }

    public void setReplyInform(Boolean replyInform) {
        this.replyInform = replyInform;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public List<Message> getReplyMessages() {
        return replyMessages;
    }

    public void setReplyMessages(List<Message> replyMessages) {
        this.replyMessages = replyMessages;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    public Integer getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(Integer adminMessage) {
        this.adminMessage = adminMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", replyInform=" + replyInform +
                ", openid='" + openid + '\'' +
                ", replyMessages=" + replyMessages +
                ", parentMessage=" + parentMessage +
                ", adminMessage=" + adminMessage +
                '}';
    }
}
