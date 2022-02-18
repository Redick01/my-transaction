package com.redick.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liupenghui
 * @date 2022/2/17 2:41 下午
 */
public interface ItpcsConfigMapper {

    //@Select("select `ID` as id ,`Name` as name ,`Value` as value ,`Remark` as remark from itpcs_config;")
    List<ItpcsConfig> selectAll();
}
