package com.sttx.zkweb.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Tree implements Serializable {

    private int id;
    private String text;
    private String state;
    public static final String STATE_OPEN = "open";
    public static final String STATE_CLOSED = "closed";
    private List<Tree> childern;
    private Boolean checked;
    private Map<String, String> attributes;
    // 数据
    private ZkNodeData zkNodeData;

    public Tree() {
    }

    public Tree(int id, String text, String state, List<Tree> childern, Map<String, String> attributes) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.childern = childern;
        this.attributes = attributes;
    }

    public Tree(int id, String text, String state, List<Tree> childern) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.childern = childern;
    }

    public Tree(int id, String text, String state, List<Tree> childern, boolean checked,
            Map<String, String> attributes) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.childern = childern;
        this.checked = checked;
        this.attributes = attributes;
    }

    public ZkNodeData getZkNodeData() {
        return zkNodeData;
    }

    public void setZkNodeData(ZkNodeData zkNodeData) {
        this.zkNodeData = zkNodeData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Tree> getChildern() {
        return childern;
    }

    public void setChildern(List<Tree> childern) {
        this.childern = childern;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

}
