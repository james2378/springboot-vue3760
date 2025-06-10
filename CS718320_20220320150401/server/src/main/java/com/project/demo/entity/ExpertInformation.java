package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 *专家信息：(ExpertInformation)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "ExpertInformation")
public class ExpertInformation implements Serializable {

    //ExpertInformation编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expert_information_id")
    private Integer expert_information_id;
   // 专家姓名
   @Basic
    private String name_of_expert;
   // 专家相片
   @Basic
    private String expert_photos;
   // 专家介绍
   @Basic
    private String expert_introduction;

    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
