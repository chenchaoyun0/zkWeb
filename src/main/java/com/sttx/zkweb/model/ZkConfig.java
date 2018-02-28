package com.sttx.zkweb.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: ZkConfig
 * @Description: zookeeper 配置信息类
 * 
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:28:23
 */
public class ZkConfig implements Serializable {
    // ID
    private String zkId;
    // 描述备注
    private String zkDescription;
    // 连接地址
    private String zkConnectStr;
    // 超时时间
    private String zkSessionTimeOut;
    // 该配置下 拥有ALL权限用户名
    private String zkUserName;
    // 密码
    private String zkUserPwd;

    public String getZkId() {
        return zkId;
    }

    public void setZkId(String zkId) {
        this.zkId = zkId;
    }

    public String getZkDescription() {
        return zkDescription;
    }

    public void setZkDescription(String zkDescription) {
        this.zkDescription = zkDescription;
    }

    public String getZkConnectStr() {
        return zkConnectStr;
    }

    public void setZkConnectStr(String zkConnectStr) {
        this.zkConnectStr = zkConnectStr;
    }

    public String getZkSessionTimeOut() {
        return zkSessionTimeOut;
    }

    public void setZkSessionTimeOut(String zkSessionTimeOut) {
        this.zkSessionTimeOut = zkSessionTimeOut;
    }

    public String getZkUserName() {
        return zkUserName;
    }

    public void setZkUserName(String zkUserName) {
        this.zkUserName = zkUserName;
    }

    public String getZkUserPwd() {
        return zkUserPwd;
    }

    public void setZkUserPwd(String zkUserPwd) {
        this.zkUserPwd = zkUserPwd;
    }

    @Override
    public String toString() {
        return "ZkConfig [zkId=" + zkId + ", zkDescription=" + zkDescription + ", zkConnectStr=" + zkConnectStr
                + ", zkSessionTimeOut=" + zkSessionTimeOut + ", zkUserName=" + zkUserName + ", zkUserPwd=" + zkUserPwd
                + "]";
    }

}
