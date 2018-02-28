package com.sttx.zkweb.model;

/**
 * 
 * @ClassName: Admin
 * @Description: zkWeb 管理员
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:27:41
 */
public class Admin {
    private String aId;
    private String adminName;
    private String adminPwd;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

}
