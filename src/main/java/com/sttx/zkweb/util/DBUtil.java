package com.sttx.zkweb.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * @ClassName: DBUtil
 * @Description: 数据库工具类，用来做简单的映射
 * @author: chenchaoyun0
 * @date: 2016年8月22日 下午1:59:34
 */
public class DBUtil {

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public static ComboPooledDataSource getDataSource() {

        return dataSource;
    }

    public static ResultSetHandler<Object[]> objectHandler = new ResultSetHandler<Object[]>() {
        public Object[] handle(ResultSet rs) throws SQLException {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            Object[] result = new Object[cols];

            for (int i = 0; i < cols; i++) {
                result[i] = rs.getObject(i + 1);
            }

            return result;
        }
    };

    public static ResultSetHandler<Map<String, Object>> mapHandler = new ResultSetHandler<Map<String, Object>>() {

        public Map<String, Object> handle(ResultSet rs) throws SQLException {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            for (int i = 0; i < cols; i++) {
                map.put(meta.getColumnName(i + 1), rs.getObject(i + 1));
            }
            return map;
        }
    };

    public static ResultSetHandler<Integer> intHandler = new ResultSetHandler<Integer>() {

        public Integer handle(ResultSet rs) throws SQLException {

            return rs.getInt(1);
        }

    };

    public static ResultSetHandler<List<Map<String, Object>>> listHandler = new ResultSetHandler<List<Map<String, Object>>>() {

        public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            ResultSetMetaData meta = rs.getMetaData();
            Map<String, Object> map = null;
            int cols = meta.getColumnCount();
            while (rs.next()) {
                map = new HashMap<String, Object>();
                for (int i = 0; i < cols; i++) {
                    map.put(meta.getColumnName(i + 1), rs.getObject(i + 1));
                }
                list.add(map);
            }

            return list;
        }
    };

}
