package com.xsh.vo;

/**
 * @author : xsh
 * @create : 2020-02-12 - 22:48
 * @describe: 自定义的查询条件实体类
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;
    private boolean published;
    private boolean draft; //判断博客是否是草稿状态，后台进行查询草稿功能

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
