package com.stelo.simpleops.common.service;

import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.common.domain.User;

public interface UserService {

    User registerUser(User user);

    User queryUserById(Long id);

    PageInfo<User> pageQueryUser(int page, int rows);

    User queryUserByUserName(String userName);

    User queryUserByIdNotNull(Long id);

    User queryUserByUserNameNotNull(String userName);

    void deleteUserById(Long id);

    void updatePasswordById(Long id, String userPassword);

    void updateUserNickNameById(Long id, String nickName);
}
