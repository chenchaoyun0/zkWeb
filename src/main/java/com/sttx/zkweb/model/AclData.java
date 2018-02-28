package com.sttx.zkweb.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: AclData
 * @Description: ACL数据实体类
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:22:32
 */
public class AclData implements Serializable {
    // 权限认证方式
    private String scheme;
    // ID
    private String id;
    // 节点权限
    private String permission;

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
