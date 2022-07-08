package com.alchemist.nowcoder.dao;

import com.alchemist.nowcoder.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeaderUrl(int id, int headerUrl);

    int updatePassword(int id, String password);
}
