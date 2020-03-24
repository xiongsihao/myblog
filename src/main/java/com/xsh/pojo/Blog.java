package com.xsh.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : xsh
 * @create : 2020-02-07 - 22:29
 * @describe: 博客实体类
 */

@Entity
@Table(name="t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id; //博客id
    private String title; //标题

    @Basic(fetch = FetchType.LAZY)
    @Lob    //@Lob注解声明大字段类型 第一次初始化时才有效，一般和@Basic懒加载一起使用，只有需要获取的时候才去查询；也可以直接去数据库内将该字段改为longtext类型
    private String content; //内容

    private String firstPicture; //首图
    private String flag; //标记
    private Integer views; //浏览次数
    private boolean appreciation; //赞赏是否开启
    private boolean shareStatement; //转载声明是否开启
    private boolean commentabled; //评论是否开启
    private boolean published; //是否发布还是保存为草稿
    private boolean recommend; //是否推荐
    @Temporal(TemporalType.TIMESTAMP)   //@Temporal指定时间的类型
    private Date createTime; //博客创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime; //博客更新时间

    @ManyToOne //指定对应关系为多对一
    private Type type; //多个博客可从属于一个分类

    @ManyToMany//(cascade = {CascadeType.PERSIST}) //cascade设置级联关系,当新增一个博客的同时如果需要新增一个标签，则也将标签新增进数据库
    private List<Tag> tags=new ArrayList<>(); //博客与标签的关系为多对多

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog") //一对多，一的一方为被维护方，mappedBy指定被维护方通过某属性连接
    private List<Comment> comments=new ArrayList<>();

    @Transient
    private  String tagIds; //博客存在多个标签，定义一个属性存放；@Transient注解声明该属性不入数据库字段

    private String description;
    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }
    //将数组转换为字符串分隔
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
    public Blog() {
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
