package com.roacg.service.tc.project.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.roacg.service.tc.project.enums.ProjectStatusEnum;
import lombok.Data;

@Data
public class ProjectVO {

    @JsonSerialize(using= ToStringSerializer.class)
    private Long projectId;

    private String projectName;

    //项目简介
    private String projectProfile;

    //源语言
    private String fromLanguage;

    //新语言
    private String toLanguage;

    //项目状态
    private ProjectStatusEnum projectStatus;
}
