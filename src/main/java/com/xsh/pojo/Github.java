package com.xsh.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : xsh
 * @create : 2020-03-22 - 16:53
 * @describe:
 */
@Entity
@Table(name = "t_githubuser")
public class Github {
    @Id
    @GeneratedValue
    private Long id;

    private String githubId;//github账号id
    private String nodeId;//github唯一标识
    private String loginTime;//首次登录博客时间
    private String createdTime;//创建github账号时间
    private String updatedTime;//最后更新github时间
    private String avatar;//github头像
    private String nickname;//github账号名字
    private String publicRepos;//公有仓库数量
    private String subscriptions;//仓库详细信息url
    private String receivedEventsUrl;//操作事件详细信息url
    private String indexUrl;//github用户首页
    private String cip;//ip地址
    private String cid;//地区编号
    private String cname;//所以在地

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(String publicRepos) {
        this.publicRepos = publicRepos;
    }

    public String getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getReceivedEventsUrl() {
        return receivedEventsUrl;
    }

    public void setReceivedEventsUrl(String receivedEventsUrl) {
        this.receivedEventsUrl = receivedEventsUrl;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Github{" +
                "id=" + id +
                ", githubId='" + githubId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nickname='" + nickname + '\'' +
                ", publicRepos='" + publicRepos + '\'' +
                ", subscriptions='" + subscriptions + '\'' +
                ", receivedEventsUrl='" + receivedEventsUrl + '\'' +
                ", indexUrl='" + indexUrl + '\'' +
                ", cip='" + cip + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
