package com.roacg.service.tc.flow.model.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 文档逻辑对象表
 */
@Data
public class DocumentObjectPO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "bigint", nullable = false)
    private Long documentId;

    //文档类型, 文件夹 or 文件
    @Column(columnDefinition = "tinyint", nullable = false)
    private Integer documentType;

    //内容类型, 文本? 链接? 附件?
    @Column(columnDefinition = "tinyint", nullable = false)
    private Integer contentType;

    //文档状态 未开始/进行中/已完成
    @Column(columnDefinition = "tinyint", nullable = false)
    private Integer documentState;

    //文件数 / 字符数?
    @Column(columnDefinition = "int", nullable = false)
    private Integer size;


    //工作流指针, 指示当前文档处于哪个节点。 每交付一次指针移动一步
    @Column(columnDefinition = "bigint", nullable = false)
    private Integer workflowNodePointer;

    //工作流头节点
    @Column(columnDefinition = "bigint", nullable = false)
    private Long workflowHeadNode;

    //工作流尾节点
    @Column(columnDefinition = "bigint", nullable = false)
    private Long workflowTailNode;

    //最终审核人(用户ID)
    @Column(columnDefinition = "bigint", nullable = false)
    private Long reviewerId;
}