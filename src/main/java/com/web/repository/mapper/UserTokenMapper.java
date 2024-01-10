package com.web.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.repository.entity.UserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper extends BaseMapper<UserTokenEntity> {
}
