package com.sttx.zkweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeRoot extends ArrayList<Tree>implements Serializable {

    Map<String, String> atr = new HashMap<String, String>();
    private Tree root = new Tree(0, "/", Tree.STATE_CLOSED, null, atr);

    public TreeRoot() {
        atr.put("path", "/");
        this.add(root);
    }

    public void setChildern(List<Tree> childern) {

        this.root.setChildern(childern);
    }
}
