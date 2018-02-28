package com.sttx.zkweb.util;

public class Const {
    // 数据编码方式
    public static final String ENCODING = "UTF-8";
    // ACL认证方式
    public static final String ZKACL_SCHEME_DIGEST = "digest";
    public static final String ZKACL_SCHEME_WORLD = "world";
    public static final String ZKACL_SCHEME_ID_ANYONE = "anyone";

    public static final int SESSION_TIMEOUT = 60 * 1000;
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int BASE_SLEEP_MS = 1000;
    public static final int RETRY_TIMES = 3;
}
