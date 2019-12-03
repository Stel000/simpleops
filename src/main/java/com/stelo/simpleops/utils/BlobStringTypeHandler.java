package com.stelo.simpleops.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class BlobStringTypeHandler extends BaseTypeHandler<String> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        byte[] bytes = s.getBytes(DEFAULT_CHARSET);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        preparedStatement.setBinaryStream(i, bis, bytes.length);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Blob blob = resultSet.getBlob(s);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        } else {
            return null;
        }

        return new String(returnValue, DEFAULT_CHARSET);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Blob blob = resultSet.getBlob(i);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        } else {
            return null;
        }

        return new String(returnValue, DEFAULT_CHARSET);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Blob blob = callableStatement.getBlob(i);
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1L, (int)blob.length());
        } else {
            return null;
        }

        return new String(returnValue, DEFAULT_CHARSET);
    }
}
