package com.stelo.simpleops.common.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {

    User selectByIdWithoutPassword(@Param("id")Long id);

    User selectOneByUsernameWithoutPassword(@Param("username")String username);

    User selectAllById(@Param("id")Long id);

    User selectOneAllByUsername(@Param("username")String username);

    List<User> selectUserWithoutPassword();
}