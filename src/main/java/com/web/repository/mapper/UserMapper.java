package com.web.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.repository.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
