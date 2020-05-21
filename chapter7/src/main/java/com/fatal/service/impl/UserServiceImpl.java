package com.fatal.service.impl;

import com.fatal.mapper.IUserMapper;
import com.fatal.entity.User;
import com.fatal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Fatal
 * @date: 2018/10/3 0003 21:54
 */
@Service
public class UserServiceImpl implements IUserService
{

    @Autowired
    private IUserMapper userMapper;  // 这里会报红线，但不影响使用

    @Override
    public User selectById(Integer id)
    {
        return userMapper.unique(id);
    }

    @Override
    public List<User> selectByName(String name)
    {
        return userMapper.selectByName(name);
    }

}
