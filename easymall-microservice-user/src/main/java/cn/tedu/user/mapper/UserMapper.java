package cn.tedu.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {
    Integer queryUsername(String userName);

    void addUser(User user);

    User login(User user);
}
