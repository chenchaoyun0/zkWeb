package com.sttx.zkweb.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @ClassName: ZkConfig
 * @Description: zookeeper 配置信息类
 * @author: chenchaoyun0
 * @date: 2016年8月21日 下午2:28:23
 */
@TableName("t_zkconfig")
@Data
public class ZkConfig implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  // ID
  @TableId
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

  @Override
  public String toString() {
    return "ZkConfig [zkId=" + zkId + ", zkDescription=" + zkDescription + ", zkConnectStr=" + zkConnectStr
        + ", zkSessionTimeOut=" + zkSessionTimeOut + ", zkUserName=" + zkUserName + ", zkUserPwd=" + zkUserPwd + "]";
  }

}
