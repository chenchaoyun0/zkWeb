package com.sttx.zkweb.service.impl;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException.NoAuthException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.sttx.zkweb.model.AclData;
import com.sttx.zkweb.model.ZkNodeData;
import com.sttx.zkweb.util.Const;

public class ZkManagerImpl {
    private final String ROOT = "/";
    private static final Logger logger = LoggerFactory.getLogger(ZkManagerImpl.class);

    public final CuratorFramework client;
    private ACL adminAcl;

    public ZkManagerImpl(String host, int timeout, String user, String pw) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(Const.BASE_SLEEP_MS, Const.RETRY_TIMES);
        client = CuratorFrameworkFactory.builder().connectString(host).sessionTimeoutMs(Const.SESSION_TIMEOUT)
                .connectionTimeoutMs(Const.CONNECTION_TIMEOUT).retryPolicy(retryPolicy).build();
        try {
            String auth = user + ":" + pw;
            Id adminId = new Id("digest", DigestAuthenticationProvider.generateDigest(auth));
            adminAcl = new ACL(Perms.ALL, adminId);
            client.start();
            client.getZookeeperClient().getZooKeeper().addAuthInfo("digest", auth.getBytes(Const.ENCODING));
            Thread.sleep(2000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean checkConnection() {
        if (!client.getZookeeperClient().isConnected()) {
            logger.info("zookeeper server is not connected.");
            client.close();
            return false;
        }
        return true;
    }

    public void disconnect() {
        if (!client.getZookeeperClient().isConnected()) {
            logger.info("zookeeper server is not connected.");
            client.close();
        }
    };

    public List<String> getChildren(String path) {
        try {
            logger.info("getChildren zookeeper :path={}", path);
            return client.getChildren().forPath(path == null ? ROOT : path);
            // return zk.getChildren(path == null ? ROOT : path, false);
        } catch (Exception e) {
            if (e instanceof NoAuthException) {// 没有权限读取该数据
                throw new RuntimeException("你没有权限访问该节点数据，请联系管理员获得用户与密码");
            }
        }
        return new ArrayList<String>();
    }

    public ZkNodeData getData(String path) throws Exception {
        logger.info("getData zookeeper :{}", path);
        ZkNodeData zkNodeData = new ZkNodeData();
        try {
            Stat stat = client.checkExists().forPath(path);
            if (stat != null) {
                byte b[] = client.getData().forPath(path);
                if (null == b) {
                    zkNodeData.setData("");
                } else {
                    // 设置数据
                    zkNodeData.setData(new String(b, Const.ENCODING));
                    logger.info("data : " + new String(b, Const.ENCODING));
                }
                // 设置meta
                setZkNodeDataMeta(stat, zkNodeData);
                // 设置Acl
                setZkNodeDataAcl(stat, path, zkNodeData);
                /* 返回 */
                return zkNodeData;
            }
        } catch (NoAuthException e) {
            logger.error("访问节点数据异常", e.getMessage(), e);
            throw new Exception("你没有权限访问该节点数据，请联系管理员获得用户与密码");
        } catch (Exception e) {
            logger.error("访问节点数据异常", e.getMessage(), e);
            throw new RuntimeException("其他异常", e);
        }
        return zkNodeData;
    }

    /**
     * 
     * @Title: setZkNodeDataMeta
     * @Description: 修改方法getNodeMeta，设置meta
     * @param stat
     * @param zkNodeData
     * @return
     * @return: ZkNodeData
     */
    private ZkNodeData setZkNodeDataMeta(Stat stat, ZkNodeData zkNodeData) {
        zkNodeData.setCzxid(String.valueOf(stat.getCzxid()));
        zkNodeData.setMzxid(String.valueOf(stat.getMzxid()));
        zkNodeData.setCtime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS").format(new Date(stat.getCtime())));
        zkNodeData.setMtime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS").format(new Date(stat.getMtime())));
        zkNodeData.setVersion(String.valueOf(stat.getVersion()));
        zkNodeData.setCversion(String.valueOf(stat.getCversion()));
        zkNodeData.setAversion(String.valueOf(stat.getAversion()));
        zkNodeData.setEphemeralOwner(String.valueOf(stat.getEphemeralOwner()));
        zkNodeData.setDataLength(String.valueOf(stat.getDataLength()));
        zkNodeData.setNumChildren(String.valueOf(stat.getNumChildren()));
        return zkNodeData;
    }

    /**
     * 
     * @Title: setZkNodeDataAcl
     * @Description: 修改代码设置ZkNodeData acllist
     * @param stat
     * @param path
     * @param zkNodeData
     * @return
     * @return: ZkNodeData
     * @throws Exception
     */
    private ZkNodeData setZkNodeDataAcl(Stat stat, String path, ZkNodeData zkNodeData) throws Exception {
        List<ACL> acls = client.getACL().forPath(path);
        AclData aclData = null;
        List<AclData> aclDatas = new ArrayList<AclData>();
        for (ACL acl : acls) {
            aclData = new AclData();
            aclData.setId(acl.getId().getId());
            aclData.setScheme(acl.getId().getScheme());
            aclData.setPermission(castPermsToString(acl.getPerms()));
            aclDatas.add(aclData);
        }
        zkNodeData.setAclDataList(aclDatas);
        return zkNodeData;
    }

    /**
     * 
     * @Title: castPermsToString
     * @Description: 将int值perms转换为cdrwa
     * @param perms
     * @return
     * @return: String
     */
    private String castPermsToString(int perms) {
        StringBuilder sb = new StringBuilder();
        if ((perms & Perms.READ) == Perms.READ) {
            sb.append(",Read");
        }
        if ((perms & Perms.WRITE) == Perms.WRITE) {
            sb.append(",Write");
        }
        if ((perms & Perms.CREATE) == Perms.CREATE) {
            sb.append(",Create");
        }
        if ((perms & Perms.DELETE) == Perms.DELETE) {
            sb.append(",Delete");
        }
        if ((perms & Perms.ADMIN) == Perms.ADMIN) {
            sb.append(",Admin");
        }
        return sb.toString().substring(sb.indexOf(",") + 1);
    }

    public boolean createNode(String path, String nodeName, String data) {
        try {
            String p;
            if (ROOT.equals(path)) {
                p = path + nodeName;
            } else {
                p = path + "/" + nodeName;
            }
            if (client.checkExists().forPath(p) == null) {
                client.create().forPath(p, data.getBytes(Const.ENCODING));
            }
            return true;
        } catch (Exception e) {
            logger.error("createNode {}", e.getMessage(), e);
        }
        return false;
    }

    public boolean createNodeOfScheme(String path, String nodeName, String data, String scheme, String nodeUser,
            String nodePassword, int[] perms, String cacheId) throws Exception {
        try {
            /**
             * 判断scheme类型
             */
            Id id1 = null;
            if (Const.ZKACL_SCHEME_DIGEST.equals(scheme)) {
                id1 = new Id(scheme, DigestAuthenticationProvider.generateDigest(nodeUser + ":" + nodePassword));
            } else if (Const.ZKACL_SCHEME_WORLD.equals(scheme)) {
                id1 = new Id(scheme, Const.ZKACL_SCHEME_ID_ANYONE);
            }
            List<ACL> acls = new ArrayList<ACL>();
            int permsv = 0;
            for (int i = 0; i < perms.length; i++) {
                permsv += perms[i];
            }
            ACL acl = new ACL(permsv, id1);
            acls.add(acl);
            acls.add(adminAcl);
            // 赋值权限结束
            String p;
            if (ROOT.equals(path)) {
                p = path + nodeName;
            } else {
                p = path + "/" + nodeName;
            }
            if (client.checkExists().forPath(p) == null) {
                client.create().withACL(acls).forPath(p, data.getBytes(Const.ENCODING));
            }
            return true;
        } catch (RuntimeException e) {
            logger.error("createNodeOfScheme error", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            if (e instanceof NoAuthException) {// 没有权限读取该数据
                logger.error("NoAuthException", e.getMessage(), e);
                throw new Exception("你没有权限访问该节点数据，请联系管理员获得用户与密码");
            }
        }
        return false;
    }

    public boolean deleteNode(String nodePath) {
        try {
            if (client.checkExists().forPath(nodePath) != null) {
                client.delete().deletingChildrenIfNeeded().forPath(nodePath);
            }
            return true;
        } catch (Exception e) {
            if (e instanceof NoAuthException) {
                throw new RuntimeException("您没有权限");
            }
            logger.error("deleteNode error:{}", e.getMessage(), e);
        }
        return false;
    }

    public boolean setData(String nodePath, String data) {
        try {
            client.setData().forPath(nodePath, data.getBytes(Const.ENCODING));
            return true;
        } catch (Exception e) {
            logger.error("setData error:{}", e.getMessage(), e);
        }
        return false;
    }

    public long getNodeId(String nodePath) {

        try {
            Stat s = client.checkExists().forPath(nodePath);
            if (s != null) {
                return s.getPzxid();
            }
        } catch (Exception e) {
            logger.error("getNodeId error:{}", e.getMessage(), e);
        }

        return 0l;
    }

    public void addAuth(String cacheId, String path, String username, String password) {
        try {
            Stat s = client.checkExists().forPath(path);
            if (s != null) {
                client.getZookeeperClient().getZooKeeper().addAuthInfo("digest",
                        (username + ":" + password).getBytes(Const.ENCODING));
            }
        } catch (Exception e) {
            logger.error("访问节点数据异常 error:{}", e.getMessage(), e);
            throw new RuntimeException("其他异常", e);
        }
    }

    public void addNodeAcl(String path, String scheme, String adminName, String adminPwd, String nodeUser,
            String nodePassword, String cacheId, int[] perms) throws Exception {
        try {
            Stat s = client.checkExists().forPath(path);
            List<ACL> acls = new ArrayList<ACL>();
            // 添加新的acl用户权限
            Id id1 = null;
            if (Const.ZKACL_SCHEME_DIGEST.equals(scheme)) {
                id1 = new Id(Const.ZKACL_SCHEME_DIGEST,
                        DigestAuthenticationProvider.generateDigest(nodeUser + ":" + nodePassword));
            } else if (Const.ZKACL_SCHEME_WORLD.equals(scheme)) {
                id1 = new Id(scheme, Const.ZKACL_SCHEME_ID_ANYONE);
            }
            int permsv = 0;
            for (int i = 0; i < perms.length; i++) {
                permsv += perms[i];
            }
            ACL acl = new ACL(permsv, id1);
            // 添加到acl list中
            acls.add(acl);
            acls.add(adminAcl);
            Stat stataddAcl = client.getZookeeperClient().getZooKeeper().setACL(path, acls, s.getAversion());
            logger.info("addNodeAcl---{}" + stataddAcl);
        } catch (NoAuthException e) {
            logger.error("添加用户acl异常，请检查admin用户及密码 :{}", e.getMessage(), e);
            throw new Exception("添加用户acl异常，请检查admin用户及密码");
        } catch (Exception e) {
            logger.error("addNodeAcl error:{}", e.getMessage(), e);
            throw new RuntimeException("addNodeAcl error", e);
        }
    }

    public void importdata(BufferedReader br) throws Exception {
        String line = null;
        while (!StringUtils.isEmpty(line = br.readLine())) {
            String[] s = line.split("::");
            if (s.length != 3)
                continue;

            String path = s[0].trim();
            String value = s[1].trim();
            String acl = s[2].trim();

            List<ACL> acls = new ArrayList<ACL>();
            acls.add(adminAcl);
            ACL aclTmp = null;

            String perm = acl.substring(acl.lastIndexOf(":") + 1);
            System.out.println(perm);

            if (acl.startsWith("world:anyone")) {
                Id id2 = new Id("world", "anyone");
                aclTmp = new ACL(ConvertPermsCharToInt(perm), id2);
            } else if (acl.startsWith("digest")) {
                String auth = acl.substring(acl.indexOf(":") + 1, acl.lastIndexOf(":"));
                System.out.println(auth);
                Id idTmp = new Id("digest", DigestAuthenticationProvider.generateDigest(auth));
                aclTmp = new ACL(ConvertPermsCharToInt(perm), idTmp);
            }
            acls.add(aclTmp);
            if (client.checkExists().forPath(path) == null)
                client.create().creatingParentsIfNeeded().withACL(acls).forPath(path, value.getBytes(Const.ENCODING));

        }
    }

    private int ConvertPermsCharToInt(String acl) {
        char[] chAcls = acl.toLowerCase().toCharArray();
        int perms = 0 << 0;

        for (char chAcl : chAcls) {
            switch (chAcl) {
            case 'r':
                perms = perms | ZooDefs.Perms.READ;
                break;
            case 'w':
                perms = perms | ZooDefs.Perms.WRITE;
                break;
            case 'c':
                perms = perms | ZooDefs.Perms.CREATE;
                break;
            case 'd':
                perms = perms | ZooDefs.Perms.DELETE;
                break;
            case 'a':
                perms = perms | ZooDefs.Perms.ADMIN;
                break;
            default:
                throw new RuntimeException("acl is not in {r w c d a}");
            }
        }

        return perms;
    }
}