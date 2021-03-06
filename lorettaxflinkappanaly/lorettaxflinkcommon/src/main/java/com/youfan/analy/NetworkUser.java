package com.lorettax.analy;

import java.io.Serializable;

/**
 * Created by li on 2018/12/16.
 */
public class NetworkUser implements Serializable{
    private String timevalue;//时间，以天为单位
    private String appId;//应用唯一标识
    private String appVersion;//版本
    private String appChannel;//渠道
    private String appPlatform;//平台
    private String network;//网络
    private String groupbyfield;//分组字段
    private Long cnts;//启动次数
    private Long newusers;//新增数
    private Long activeusers;//活跃数

    public String getTimevalue() {
        return timevalue;
    }

    public void setTimevalue(String timevalue) {
        this.timevalue = timevalue;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getAppPlatform() {
        return appPlatform;
    }

    public void setAppPlatform(String appPlatform) {
        this.appPlatform = appPlatform;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getGroupbyfield() {
        return groupbyfield;
    }

    public void setGroupbyfield(String groupbyfield) {
        this.groupbyfield = groupbyfield;
    }

    public Long getCnts() {
        return cnts;
    }

    public void setCnts(Long cnts) {
        this.cnts = cnts;
    }

    public Long getNewusers() {
        return newusers;
    }

    public void setNewusers(Long newusers) {
        this.newusers = newusers;
    }

    public Long getActiveusers() {
        return activeusers;
    }

    public void setActiveusers(Long activeusers) {
        this.activeusers = activeusers;
    }

    @Override
    public String toString() {
        return "NetworkUser{" +
                "timevalue='" + timevalue + '\'' +
                ", appId='" + appId + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appChannel='" + appChannel + '\'' +
                ", appPlatform='" + appPlatform + '\'' +
                ", network='" + network + '\'' +
                ", groupbyfield='" + groupbyfield + '\'' +
                ", cnts=" + cnts +
                ", newusers=" + newusers +
                ", activeusers=" + activeusers +
                '}';
    }
}
