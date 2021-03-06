package com.roacg.service.tc.team.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 一个小组的详情
 */
@Data
public class TeamDetailVO {

    private Long teamId;

    private String teamName;

    private Long leaderId;

    private String avatar;

    //团队简介
    private String teamProfile;

    //团队说明
    private String teamDescription;

    //团队等级,根据等级 相应的权限也有所不同
    private Integer teamGrade;

    //团队当前人数
    private Integer teamSize;

    //团队已创建的项目数量
    private Integer projectNum;

    //创建时间
    private LocalDate createTime;

    /**
     * 小组下的用户
     */
    private List<TeamSimpleUserVO> users;
}
