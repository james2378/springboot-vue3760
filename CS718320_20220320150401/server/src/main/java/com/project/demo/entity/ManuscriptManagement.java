package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *稿件管理：(ManuscriptManagement)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "ManuscriptManagement")
public class ManuscriptManagement implements Serializable {

    //ManuscriptManagement编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manuscript_management_id")
    private Integer manuscript_management_id;
   // 稿件编号
   @Basic
    private String manuscript_no;
   // 稿件名称
   @Basic
    private String manuscript_name;
   // 稿件类型
   @Basic
    private String manuscript_type;
   // 投稿人
   @Basic
    private Integer contributor;
   // 上传日期
   @Basic
    private Timestamp upload_date;
   // 备注
   @Basic
    private String remarks;
   // 稿件附件
   @Basic
    private String manuscript_attachment;
   // 稿件审核状态
   @Basic
    private String manuscript_review_status;
   // 专家用户
   @Basic
    private Integer expert_user;
   // 稿件简介
   @Basic
    private String introduction_to_manuscript;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
