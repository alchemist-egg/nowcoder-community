package com.alchemist.nowcoder.dao;

import com.alchemist.nowcoder.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);

    int selectDiscussPostRows(@Param("userId") int userId);
}