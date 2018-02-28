package com.sttx.zkweb.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @ClassName: ZkNodeData
 * @Description: 节点数据类
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:30:02
 */
public class ZkNodeData implements Serializable {
    private String data;
    private String czxid;
    private String mzxid;
    private String ctime;
    private String mtime;
    private String version;
    private String cversion;
    private String aversion;
    private String ephemeralOwner;
    private String dataLength;
    private String numChildren;
    private String pzxid;
    private List<AclData> aclDataList;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCzxid() {
        return czxid;
    }

    public void setCzxid(String czxid) {
        this.czxid = czxid;
    }

    public String getMzxid() {
        return mzxid;
    }

    public void setMzxid(String mzxid) {
        this.mzxid = mzxid;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCversion() {
        return cversion;
    }

    public void setCversion(String cversion) {
        this.cversion = cversion;
    }

    public String getAversion() {
        return aversion;
    }

    public void setAversion(String aversion) {
        this.aversion = aversion;
    }

    public String getEphemeralOwner() {
        return ephemeralOwner;
    }

    public void setEphemeralOwner(String ephemeralOwner) {
        this.ephemeralOwner = ephemeralOwner;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(String numChildren) {
        this.numChildren = numChildren;
    }

    public String getPzxid() {
        return pzxid;
    }

    public void setPzxid(String pzxid) {
        this.pzxid = pzxid;
    }

    public List<AclData> getAclDataList() {
        return aclDataList;
    }

    public void setAclDataList(List<AclData> aclDataList) {
        this.aclDataList = aclDataList;
    }

}
