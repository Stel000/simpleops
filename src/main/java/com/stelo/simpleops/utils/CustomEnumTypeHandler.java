package com.stelo.simpleops.utils;

import com.stelo.simpleops.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomEnumTypeHandler extends BaseTypeHandler<BaseEnum> {

    private Class<BaseEnum> type;

    public CustomEnumTypeHandler(Class<BaseEnum> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BaseEnum baseEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,baseEnum.getCode());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return convertToBaseEnum(resultSet.getInt(s));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return convertToBaseEnum(resultSet.getInt(i));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return convertToBaseEnum(callableStatement.getInt(i));
    }

    private BaseEnum convertToBaseEnum(int status){
        BaseEnum[] objs = type.getEnumConstants();
        for(BaseEnum em: objs){
            if(em.getCode().equals(status)){
                return  em;
            }
        }
        return null;
    }
}
