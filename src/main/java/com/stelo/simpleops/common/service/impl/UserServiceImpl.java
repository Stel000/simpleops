package com.stelo.simpleops.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stelo.simpleops.common.domain.User;
import com.stelo.simpleops.common.exception.UserException;
import com.stelo.simpleops.common.mapper.UserMapper;
import com.stelo.simpleops.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectByIdWithoutPassword(id);
    }

    @Override
    public PageInfo<User> pageQueryUser(int page, int rows) {
        return PageHelper.startPage(page, rows).doSelectPageInfo(() -> userMapper.selectUserWithoutPassword());
    }

    @Override
    public User queryUserByUserName(String userName) {
        return userMapper.selectOneByUsernameWithoutPassword(userName);
    }

    @Override
    public User queryUserByIdNotNull(Long id) {
        return Optional.ofNullable(userMapper.selectAllById(id))
                .orElseThrow(() -> new UserException("user.error.no_exist"));
    }

    @Override
    public User queryUserByUserNameNotNull(String userName) {
        return Optional.ofNullable(userMapper.selectOneAllByUsername(userName))
                .orElseThrow(() -> new UserException("user.error.no_exist"));
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updatePasswordById(Long id, String userPassword) {
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(userPassword));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void updateUserNickNameById(Long id, String nickName) {
        User user = new User();
        user.setId(id);
        user.setNickname(nickName);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
